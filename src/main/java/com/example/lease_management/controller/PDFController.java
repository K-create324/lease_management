package com.example.lease_management.controller;

import com.example.lease_management.Client;
import com.example.lease_management.Contract;
import com.example.lease_management.service.ClientService;
import com.example.lease_management.service.ContractService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/pdf")
public class PDFController {
    private final ContractService contractService;
    private final ClientService clientService;

    public PDFController(ContractService contractService, ClientService clientService) {
        this.contractService = contractService;
        this.clientService = clientService;
    }


    @GetMapping("/contract/{id}")
    public ResponseEntity<byte[]> getPDFfile(@PathVariable Integer id) {
        Optional<Contract> contrcOpt = contractService.getOneContract(id);
        if (contrcOpt.isPresent()) {
            Contract contract = contrcOpt.get();
            byte[] pdfFile = contract.getPdfFile();
            if (pdfFile != null && pdfFile.length > 0) {
                HttpHeaders header = new HttpHeaders();
                header.setContentType(MediaType.APPLICATION_PDF);
                header.setContentDisposition(ContentDisposition.inline().filename("umowa_" + id + ".pdf").build());
                return new ResponseEntity<>(pdfFile, header, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
        @GetMapping("/client/{id}")
                public ResponseEntity <byte[]> getpdfFileClient(@PathVariable Integer id){
            Optional<Client> oneClient = clientService.getOneClient(id);
            if(oneClient.isPresent()) {
                Client client = oneClient.get();
                byte[] pdfFile = client.getPdfFile();
                if (pdfFile != null && pdfFile.length > 0) {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_PDF);
                    httpHeaders.setContentDisposition(ContentDisposition.inline().filename("klient o id: " + id + " pdf").build());
                    return new ResponseEntity<>(pdfFile, httpHeaders, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }else{
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

}}



