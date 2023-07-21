package com.eric.entity;

import com.eric.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
public class RemoveHoliday {

    private String countryCode;
    private Date holidayDate;

    @JsonIgnore
    public boolean isValid() {
        return StringUtils.isNoneBlank(countryCode) && holidayDate != null;
    }

    @JsonIgnore
    public String getPk() {
        return countryCode.toLowerCase() + DateUtil.formatDateToyyyyMMdd(holidayDate);
    }
    
}
