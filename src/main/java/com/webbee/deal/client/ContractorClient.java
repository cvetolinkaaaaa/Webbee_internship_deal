package com.webbee.deal.client;

import com.webbee.deal.dto.ContractorDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContractorClient {

    private final RestTemplate restTemplate;
    private final String contractorServiceUrl;

    public ContractorClient(RestTemplate restTemplate,
                            @Value("${contractor.service.url}") String contractorServiceUrl) {
        this.restTemplate = restTemplate;
        this.contractorServiceUrl = contractorServiceUrl;
    }

    public ContractorDto getContractor(String contractorId) {
        String url = contractorServiceUrl + "/contractor/" + contractorId;
        ContractorDto contractor = restTemplate.getForObject(url, ContractorDto.class);
        return contractor;
    }

}
