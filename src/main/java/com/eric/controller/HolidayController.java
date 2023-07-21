package com.eric.controller;

// rest controller

import com.eric.entity.*;
import com.eric.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class HolidayController {

    @Autowired
    private HolidayService holidayService;


    @PostMapping("/holiday/add")
    public Map<AddHoliday, String> addHoliday(@RequestBody List<AddHoliday> list) {
        return holidayService.addHoliday(list);
    }

    @PostMapping("/holiday/update")
    public Map<UpdateHoliday, String> updateHoliday(@RequestBody List<UpdateHoliday> list) {
        return holidayService.updateHoliday(list);
    }

    @PostMapping("/holiday/remove")
    public Map<RemoveHoliday, String> removeHoliday(@RequestBody List<RemoveHoliday> list) {
        return holidayService.removeHoliday(list);
    }

    // get all holidays
    @PostMapping("/holiday/getAll")
    public Set<HolidayDTO> getAllHolidays() {
        return holidayService.getAllHolidays();
    }

    // get all holidays for given country in next year
    @GetMapping("/holiday/getAllForNextYear")
    public Set<HolidayDTO> getAllHolidaysForNextYear(@RequestParam String country) {
        return holidayService.getAllHolidaysForNextYear(country);
    }

    // get next holiday for given country
    @GetMapping("/holiday/getNextHoliday")
    public HolidayDTO getNextHoliday(@RequestParam String country) {
        return holidayService.getNextHoliday(country);
    }

    // check if given date is holiday
    @GetMapping("/holiday/isHoliday")
    public Map<String, Boolean> isHoliday(@RequestParam String date) {
        return holidayService.isHoliday(date);
    }

}
