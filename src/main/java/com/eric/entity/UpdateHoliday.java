package com.eric.entity;

import com.eric.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
public class UpdateHoliday {

    private String countryCode;
    private String countryDesc;
    private Date holidayDate;
    private String holidayDesc;
    
    // is valid
    @JsonIgnore
    public boolean isValid() {
        return StringUtils.isNoneBlank(countryCode, countryDesc, holidayDesc) && holidayDate != null;
    }

    @JsonIgnore
    public String getPk() {
        return countryCode.toLowerCase() + DateUtil.formatDateToyyyyMMdd(holidayDate);
    }
}
