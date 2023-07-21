package com.eric.entity;

import com.eric.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {

    private String pk;
    private String countryCode;
    private String countryDesc;
    private Date holidayDate;
    private String holidayDesc;

    @JsonIgnore
    public boolean isValid() {
        return StringUtils.isNoneBlank(pk, countryCode, countryDesc, holidayDesc) && holidayDate != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Holiday that = (Holiday) o;

        return new EqualsBuilder().append(pk, that.pk).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(pk).toHashCode();
    }

    // to HolidayDTO
    public HolidayDTO toHolidayDTO() {
        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setCountryCode(countryCode);
        holidayDTO.setCountryDesc(countryDesc);
        holidayDTO.setHolidayDate(DateUtil.formatDate(holidayDate));
        holidayDTO.setHolidayDesc(holidayDesc);
        return holidayDTO;
    }
}
