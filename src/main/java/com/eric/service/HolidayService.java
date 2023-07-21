package com.eric.service;
// service

import com.eric.entity.*;
import com.eric.util.DateUtil;
import com.eric.util.HolidayDTOCsvUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HolidayService {

    public Map<AddHoliday, String> addHoliday(List<AddHoliday> list) {
        Map<AddHoliday, String> res = new HashMap<>();

        Set<Holiday> holidayDTOS = HolidayDTOCsvUtil.readAll();
        for (AddHoliday ele : list) {
            String result;
            if (ele != null && ele.isValid()) {
                Holiday holidayDTO = new Holiday();
                BeanUtils.copyProperties(ele, holidayDTO);
                holidayDTO.setPk(ele.getPk());
                if (holidayDTOS.contains(holidayDTO)) {
                    result = "won't add since holiday already exist";
                } else {
                    holidayDTOS.add(holidayDTO);
                    result = "added";
                }
            } else {
                result = "invalid request";
            }
            res.put(ele, result);
        }
        HolidayDTOCsvUtil.saveAll(holidayDTOS);
        return res;
    }

    public Map<UpdateHoliday, String> updateHoliday(List<UpdateHoliday> list) {
        Map<UpdateHoliday, String> res = new HashMap<>();

        Set<Holiday> holidayDTOS = HolidayDTOCsvUtil.readAll();
        for (UpdateHoliday ele : list) {
            String result;
            if (ele != null && ele.isValid()) {
                Holiday holidayDTO = new Holiday();
                BeanUtils.copyProperties(ele, holidayDTO);
                holidayDTO.setPk(ele.getPk());
                if (holidayDTOS.contains(holidayDTO)) {
                    result = "updated";
                    // remove then add: update
                    holidayDTOS.remove(holidayDTO);
                    holidayDTOS.add(holidayDTO);
                } else {
                    result = "won't update since holiday not exist";
                }
            } else {
                result = "invalid request";
            }
            res.put(ele, result);
        }
        HolidayDTOCsvUtil.saveAll(holidayDTOS);
        return res;
    }

    public Map<RemoveHoliday, String> removeHoliday(List<RemoveHoliday> list) {
        Map<RemoveHoliday, String> res = new HashMap<>();

        Set<Holiday> holidayDTOS = HolidayDTOCsvUtil.readAll();
        for (RemoveHoliday ele : list) {
            String result;
            if (ele != null && ele.isValid()) {
                Holiday holidayDTO = new Holiday();
                holidayDTO.setPk(ele.getPk());
                if (holidayDTOS.contains(holidayDTO)) {
                    result = "removed";
                    // remove then add: update
                    holidayDTOS.remove(holidayDTO);
                } else {
                    result = "won't remove since holiday not exist";
                }
            } else {
                result = "invalid request";
            }
            res.put(ele, result);
        }
        HolidayDTOCsvUtil.saveAll(holidayDTOS);
        return res;
    }

    // get all holidays
    public Set<HolidayDTO> getAllHolidays() {
        return HolidayDTOCsvUtil.readAll().stream().map(Holiday::toHolidayDTO).collect(Collectors.toSet());
    }

    // get all holidays for given country in next year
    public Set<HolidayDTO> getAllHolidaysForNextYear(String country) {
        if (StringUtils.isEmpty(country)) {
            return new HashSet<>();
        }
        Set<Holiday> holidayDTOS = HolidayDTOCsvUtil.readAll();
        holidayDTOS.removeIf(holidayDTO -> !holidayDTO.getCountryCode().equalsIgnoreCase(country));
        holidayDTOS.removeIf(holidayDTO -> DateUtil.convertToLocalDateViaInstant(holidayDTO.getHolidayDate()).getYear() != LocalDate.now().getYear() + 1);
        return holidayDTOS.stream().map(Holiday::toHolidayDTO).collect(Collectors.toSet());
    }

    // get next holiday for given country
    public HolidayDTO getNextHoliday(String country) {
        if (StringUtils.isEmpty(country)) {
            return null;
        }
        Set<Holiday> holidayDTOS = HolidayDTOCsvUtil.readAll();
        holidayDTOS.removeIf(holidayDTO -> !holidayDTO.getCountryCode().equalsIgnoreCase(country));
        if (holidayDTOS.isEmpty()) {
            return null;
        }
        Holiday res = null;
        for (Holiday holidayDTO : holidayDTOS) {
            if (holidayDTO.getHolidayDate().after(new Date())) {
                if (res == null) {
                    res = holidayDTO;
                } else {
                    if (holidayDTO.getHolidayDate().before(res.getHolidayDate())) {
                        res = holidayDTO;
                    }
                }
            }
        }
        return res == null ? null : res.toHolidayDTO();
    }

    // check if given date is holiday
    @SneakyThrows
    public Map<String, Boolean> isHoliday(String dateStr) {
        Map<String, Boolean> res = new HashMap<>();
        if (!DateUtil.isValidDate(dateStr)) {
            return res;
        }
        Date date = DateUtil.parseDate(dateStr);
        Set<Holiday> holidayDTOS = HolidayDTOCsvUtil.readAll();
        Set<String> countries = holidayDTOS.stream().map(Holiday::getCountryCode).collect(Collectors.toSet());
        countries.forEach(country -> {
            Holiday holiday = new Holiday();
            holiday.setPk(country + DateUtil.formatDateToyyyyMMdd(date));
            res.put(country, holidayDTOS.contains(holiday));
        });
        return res;
    }
}
