package com.eric.util;

import com.eric.entity.Holiday;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class HolidayDTOCsvUtil {

    // read csv file and return a list of objects
    @SneakyThrows
    public static Set<Holiday> readAll() {
        Set<Holiday> holidayDTOList = new HashSet<>();
        ClassPathResource resource = new ClassPathResource("holiday.csv");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                Holiday holidayDTO = new Holiday();
                String[] fields = line.split(",");
                holidayDTO.setPk(fields[0]);
                holidayDTO.setCountryCode(fields[1]);
                holidayDTO.setCountryDesc(fields[2]);
                holidayDTO.setHolidayDate(DateUtil.parseDate(fields[3]));
                holidayDTO.setHolidayDesc(fields[4]);
                holidayDTOList.add(holidayDTO);
            }
        }
        return holidayDTOList;
    }

    // write holidayDTOList to csv file
    @SneakyThrows
    public synchronized static void saveAll(Set<Holiday> holidayDTOList) {
        holidayDTOList.removeIf(holidayDTO -> holidayDTO == null || !holidayDTO.isValid());
        ClassPathResource resource = new ClassPathResource("holiday.csv");
        String header = "pk,countryCode,countryDesc,holidayDate,holidayDesc";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resource.getFile(), false))) {
            bufferedWriter.write(header);
            bufferedWriter.newLine();
            for (Holiday holidayDTO : holidayDTOList) {
                bufferedWriter.write(String.join(",",
                        holidayDTO.getPk(),
                        holidayDTO.getCountryCode(),
                        holidayDTO.getCountryDesc(),
                        DateUtil.formatDate(holidayDTO.getHolidayDate()),
                        holidayDTO.getHolidayDesc()));
                bufferedWriter.newLine();
            }
        }
    }
}
