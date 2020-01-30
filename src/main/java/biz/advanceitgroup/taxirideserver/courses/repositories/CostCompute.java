/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.courses.repositories;

import biz.advanceitgroup.taxirideserver.courses.entities.Trip;
import biz.advanceitgroup.taxirideserver.courses.entities.TripOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniel
 */
@Repository
public class CostCompute extends JdbcDaoSupport{
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDs(DataSource dataSource) {
         setDataSource((javax.sql.DataSource) dataSource);
    }
    
    public int computeFees(Trip trip){
        String formula=trip.getTripOptionFeeFormula();
        int courseCost;
        courseCost = (int)jdbcTemplate.queryForObject(formula, new Object[]{trip.getTripID()}, new IntMapper());
        return courseCost;
    }
    public int computeFeesForSample(TripOption tripOption,long estimatedDistance, long estimatedDuration){
        String formula=tripOption.getTripFeeFormula();
        int courseCost;
        courseCost = (int)jdbcTemplate.queryForObject(formula, new Object[]{estimatedDistance,estimatedDuration,tripOption.getOptionCode()},Integer.class);
        return courseCost;
    }
    
    public double computeDriverFees(Trip trip){
         String formula=trip.getTripOptionDriverCancelFeeFormula();
         double cost;
         cost=(double)jdbcTemplate.queryForObject(formula, new Object[]{trip.getTripID()}, Integer.class);
         return cost;
    }
   
    public double computeRiderFees(Trip trip){
         String formula=trip.getTripOptionRiderCancelFeeFormula();
         double cost;
         cost=(double)jdbcTemplate.queryForObject(formula, new Object[]{trip.getTripID()}, Integer.class);
         return cost;
    }
}
 class IntMapper implements RowMapper {

            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                int tripCost=rs.getInt("trip_cost");
                return tripCost;
            }

         }
