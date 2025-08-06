package com.example.csvloader.service;

import com.example.csvloader.model.Login;
import com.example.csvloader.model.Posting;
import com.example.csvloader.repository.LoginRepository;
import com.example.csvloader.repository.PostingRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.function.Function;

@Service
public class CsvLoadService {

    private final LoginRepository loginRepo;
    private final PostingRepository postingRepo;


    @Value("classpath:data/logins.csv")
    private Resource loginsCsv;

    @Value("classpath:data/postings.csv")
    private Resource postingsCsv;

    public CsvLoadService(LoginRepository loginRepo, PostingRepository postingRepo) {
        this.loginRepo = loginRepo;
        this.postingRepo = postingRepo;
    }

    @PostConstruct
    @Transactional
    public void loadData() throws Exception {

        postingRepo.deleteAllInBatch();
        loginRepo.deleteAllInBatch();

        // Импорт логинов: id, app_account_name, is_active, group_name, position
        loadCsv(
                loginsCsv,
                ',',
                parts -> {
                    String systemCode  = parts[0].trim();
                    String username    = parts[1].trim();
                    boolean active     = Boolean.parseBoolean(parts[2].trim());
                    String groupName   = parts[3].trim();
                    String position    = parts[4].trim();

                    Login login = loginRepo.findByAppAccountName(username)
                            .orElseGet(Login::new);

                    login.setSystemCode(systemCode);
                    login.setAppAccountName(username);
                    login.setIsActive(active);
                    login.setGroupName(groupName);
                    login.setPosition(position);

                    return loginRepo.save(login);
                }
        );

        // Импорт postings
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        loadCsv(postingsCsv, ';', parts -> {
            if (parts.length < 10) {
                System.err.println("Bad postings line: " + Arrays.toString(parts));
                return null;
            }
            Posting p = new Posting();
            p.setMatDoc(parts[0].trim());
            p.setItem(Integer.parseInt(parts[1].trim()));
            p.setDocDate(LocalDate.parse(parts[2].trim(), dtf));
            p.setPstngDate(LocalDate.parse(parts[3].trim(), dtf));
            p.setMaterialDescription(parts[4].trim());
            p.setQuantity(new BigDecimal(
                    parts[5].trim()
                            .replace("\u00A0", "")
                            .replace(" ", "")
                            .replace(",", ".")
            ));
            p.setBun(parts[6].trim());
            p.setAmountLc(new BigDecimal(
                    parts[7].trim()
                            .replace("\u00A0", "")
                            .replace(" ", "")
                            .replace(",", ".")
            ));
            p.setCrcy(parts[8].trim());
            p.setUserName(parts[9].trim());

            boolean auth = loginRepo.findByAppAccountName(p.getUserName())
                    .map(Login::getIsActive)
                    .orElse(false);
            p.setAuthorizedSupply(auth);

            return postingRepo.save(p);
        });
    }

    /*
     * Универсальный CSV-импортёр:
     * @param resource — CSV-файл из classpath
     * @param separator — разделитель колонок (',' или ';' и т.п.)
     * @param mapper    — функция, превращающая String[] в вашу сущность и сохраняющая её
     */
    private <T> void loadCsv(
            Resource resource,
            char separator,
            Function<String[], T> mapper
    ) throws Exception {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(true)
                .build();

        try ( var reader = new InputStreamReader(resource.getInputStream());
              var csv    = new CSVReaderBuilder(reader)
                      .withSkipLines(1)
                      .withCSVParser(parser)
                      .build()
        ) {
            csv.forEach(parts -> {
                if (parts.length == 0) {
                    return;
                }
                mapper.apply(parts);
            });
        }
    }
}
