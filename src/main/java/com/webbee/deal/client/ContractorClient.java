package com.webbee.deal.client;

import com.webbee.deal.dto.ContractorDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Клиент для взаимодействия с внешним сервисом контрагентов.
 */
@Component
public class ContractorClient {

    private final RestTemplate restTemplate;
    private final String contractorServiceUrl;

    public ContractorClient(RestTemplate restTemplate,
                            @Value("${contractor.service.url}") String contractorServiceUrl) {
        this.restTemplate = restTemplate;
        this.contractorServiceUrl = contractorServiceUrl;
    }

    /**
     * Получает контрагента по его id.
     */
    public ContractorDto getContractor(String contractorId) {
        String url = contractorServiceUrl + "/contractor/" + contractorId;
        ContractorDto contractor = restTemplate.getForObject(url, ContractorDto.class);
        return contractor;
    }

}
