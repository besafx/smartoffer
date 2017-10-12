package com.besafx.app.rest;

import com.besafx.app.entity.Bank;
import com.besafx.app.entity.Person;
import com.besafx.app.search.BankSearch;
import com.besafx.app.service.BankService;
import com.besafx.app.service.BranchService;
import com.besafx.app.service.PersonService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/bank/")
public class BankRest {

    private final static Logger log = LoggerFactory.getLogger(BankRest.class);

    private final String FILTER_TABLE = "**,branch[id,code,name],lastPerson[id,contact[id,firstName,forthName]]";
    private final String FILTER_BANK_COMBO = "id,code,name,branchName,stock,branch[id,code,name]";

    @Autowired
    private PersonService personService;

    @Autowired
    private BankService bankService;

    @Autowired
    private BankSearch bankSearch;

    @Autowired
    private BranchService branchService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_BANK_CREATE')")
    public String create(@RequestBody Bank bank, Principal principal) {
        Person person = personService.findByEmail(principal.getName());
        bank.setLastUpdate(new Date());
        bank.setLastPerson(person);
        bank.setStock(bank.getStartAmount());
        bank = bankService.save(bank);
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على قواعد البيانات")
                .message("تم اضافة حساب بنك جديد بنجاح")
                .type("success")
                .icon("fa-plus-square")
                .build(), principal.getName());
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), bank);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_BANK_UPDATE')")
    public String update(@RequestBody Bank bank, Principal principal) {
        Bank object = bankService.findOne(bank.getId());
        if (object != null) {
            Person person = personService.findByEmail(principal.getName());
            bank.setLastUpdate(new Date());
            bank.setLastPerson(person);
            bank = bankService.save(bank);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على قواعد البيانات")
                    .message("تم تعديل بيانات حساب البنك رقم " + bank.getCode() + " بنجاح.")
                    .type("warn")
                    .icon("fa-plus-square")
                    .build(), principal.getName());
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), bank);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_BANK_DELETE')")
    public void delete(@PathVariable Long id, Principal principal) {
        Bank object = bankService.findOne(id);
        if (object != null) {
            bankService.delete(id);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على قواعد البيانات")
                    .message("تم حذف حساب بنك بنجاح")
                    .type("error")
                    .icon("fa-plus-square")
                    .build(), principal.getName());
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findAll() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), Lists.newArrayList(bankService.findAll()));
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOne(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), bankService.findOne(id));
    }

    @RequestMapping(value = "filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filter(
            @RequestParam(value = "code", required = false) final Long code,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "branchName", required = false) final String branchName,
            @RequestParam(value = "stockFrom", required = false) final Long stockFrom,
            @RequestParam(value = "stockTo", required = false) final Long stockTo,
            @RequestParam(value = "branchId", required = false) final Long branchId) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                bankSearch.search(code, name, branchName, stockFrom, stockTo, branchId));
    }

    @RequestMapping(value = "fetchTableData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String fetchTableData(Principal principal) {
        Person person = personService.findByEmail(principal.getName());
        if(Arrays.asList(person.getTeam().getAuthorities().split(",")).contains("ROLE_BRANCH_FULL_CONTROL")){
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                    Lists.newArrayList(branchService.findAll()).stream()
                            .flatMap(branch -> branch.getBanks().stream())
                            .collect(Collectors.toList()));
        }else{
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), person.getBranch().getBanks());
        }
    }

    @RequestMapping(value = "fetchTableDataCombo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String fetchTableDataCombo(Principal principal) {
        Person person = personService.findByEmail(principal.getName());
        if(Arrays.asList(person.getTeam().getAuthorities().split(",")).contains("ROLE_BRANCH_FULL_CONTROL")){
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_BANK_COMBO),
                    Lists.newArrayList(branchService.findAll()).stream()
                            .flatMap(branch -> branch.getBanks().stream())
                            .collect(Collectors.toList()));
        }else{
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_BANK_COMBO), person.getBranch().getBanks());
        }
    }

}
