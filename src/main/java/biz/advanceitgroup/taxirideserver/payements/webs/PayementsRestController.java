/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.payements.webs;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.UserService;
import biz.advanceitgroup.taxirideserver.commons.entities.ResponseNew;
import biz.advanceitgroup.taxirideserver.commons.services.CommonServices;
import biz.advanceitgroup.taxirideserver.payements.Dtos.RefillAccountDto;
import biz.advanceitgroup.taxirideserver.payements.Dtos.WithdrawMoneyDto;
import biz.advanceitgroup.taxirideserver.payements.entities.UserAccountHistory;
import biz.advanceitgroup.taxirideserver.payements.entities.UserDeposit;
import biz.advanceitgroup.taxirideserver.payements.entities.UserWithdrawal;
import biz.advanceitgroup.taxirideserver.payements.services.interfaces.PayementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daniel
 */
@Api(value = "Payements Rest Api")
@RestController
@RequestMapping("/api/payements")
public class PayementsRestController {
    
    @Autowired
    CommonServices commonServices;
    
    @Autowired
    PayementService payementService;
    
    @Autowired
    UserService userService;
    
    @Secured({"ROLE_RIDER","ROLE_DRIVER","ROLE_ADMIN"}) 
    @ApiOperation(value = "Cash in money in the application by a User")
    @PostMapping("/refillAccount")
    public ResponseEntity<?> refillAccount(@Valid @RequestBody RefillAccountDto refillAccount){
        try{
            
            Optional<Users> user=userService.findByEmailOrPhone(refillAccount.getEmailOrPhone());
            
            if(user.isPresent()){
                
                Optional<UserAccountHistory> currentHistory=payementService.checkIfExist(user.get().getId());
                if(currentHistory.isPresent()){
                    
                    double currentAmount=currentHistory.get().getCurrentBalance()+refillAccount.getAmount();
                     //inserts the amount in the userDeposit table
                    UserDeposit userDeposit=payementService.refillUserAccount(refillAccount.getAmount(),currentAmount,user.get().getUserPaymentModes().get(0).getValue(), user.get().getUserPaymentModes().get(0).getPaymentMode().getNameEn(),user.get().getUserPaymentModes().get(0).getId(), user.get().getId());

                    //inserts the traces of the transaction in the internal account table
                    payementService.refillAccount(refillAccount.getAmount(), refillAccount.getAccountType(), refillAccount.getRefillingMode());

                    //saves the traces in the userHistory table
                    UserAccountHistory accountHistory=payementService.saveHistoryUserAccount(new Date(), user.get().getId(),1 , currentHistory.get().getCurrentBalance(), userDeposit.getNewBalance());
                    
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200,refillAccount.getLanguage()),null));
                }
                //inserts the amount in the userDeposit table
                UserDeposit userDeposit=payementService.refillUserAccount(refillAccount.getAmount(),refillAccount.getAmount(),user.get().getUserPaymentModes().get(0).getValue(), user.get().getUserPaymentModes().get(0).getPaymentMode().getNameEn(),user.get().getUserPaymentModes().get(0).getId(), user.get().getId());
                double previewBalance=userDeposit.getNewBalance()-userDeposit.getAmount();
                
                //inserts the traces of the transaction in the internal account table
                payementService.refillAccount(refillAccount.getAmount(), refillAccount.getAccountType(), refillAccount.getRefillingMode());
                
                //saves the traces in the userHistory table
                UserAccountHistory accountHistory=payementService.saveHistoryUserAccount(new Date(), user.get().getId(),1 , previewBalance, userDeposit.getNewBalance());
                
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200,refillAccount.getLanguage()),null));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(722, commonServices.findErrorByCode(722, refillAccount.getLanguage()), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(853, commonServices.findErrorByCode(853,refillAccount.getLanguage()),null));
        }
    }
    
    @Secured({"ROLE_RIDER","ROLE_DRIVER","ROLE_ADMIN"}) 
    @ApiOperation(value = "Withdraw Money from an account by a user")
    @PostMapping("/WithdrawMoney")
    public ResponseEntity<?> withdrawMoney(@Valid @RequestBody WithdrawMoneyDto withdrawMoneyDto){
        try{
            Optional<Users> user=userService.findByEmailOrPhone(withdrawMoneyDto.getEmailOrPhone());
            if(user.isPresent()){
                Optional<UserAccountHistory> currentHistory=payementService.checkIfExist(user.get().getId());
                if(currentHistory.isPresent() && currentHistory.get().getCurrentBalance()> withdrawMoneyDto.getAmount()){
                    //saves the history of a user account after the withdrawal
                    UserAccountHistory accountHistory=payementService.saveHistoryUserAccount(new Date(), user.get().getId(), 4, currentHistory.get().getCurrentBalance(), currentHistory.get().getCurrentBalance()-withdrawMoneyDto.getAmount());
                    
                    //create a new line in the userWithdrawal table
                    UserWithdrawal userWithdrawal=payementService.withdrawFromUserAccount(user.get().getId(), withdrawMoneyDto.getAmount(), currentHistory.get().getCurrentBalance()-withdrawMoneyDto.getAmount(), user.get().getUserPaymentModes().get(0).getId(), user.get().getUserPaymentModes().get(0).getPaymentMode().getNameEn(), user.get().getUserPaymentModes().get(0).getValue());;

                    //modifies the internal account table
                    payementService.withdrawMoney(withdrawMoneyDto.getAmount(), withdrawMoneyDto.getPayementMode());
                    
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, withdrawMoneyDto.getLanguage()), null));
                }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(857,commonServices.findErrorByCode(857, withdrawMoneyDto.getLanguage()),null));   
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(722,commonServices.findErrorByCode(722, withdrawMoneyDto.getLanguage()),null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(856,commonServices.findErrorByCode(856, withdrawMoneyDto.getLanguage()),null));
        }
    }
    
    
}
