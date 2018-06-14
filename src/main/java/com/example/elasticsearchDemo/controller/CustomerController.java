package com.example.elasticsearchDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.elasticsearchDemo.model.Customer;
import com.example.elasticsearchDemo.repository.CustomerRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import static com.example.elasticsearchDemo.ConfigProperties.CLUSTER_NAME;

/**
 * ConsumerController.
 *
 * @author gao jx
 */
@RestController
@RequestMapping("/customer")
public final class CustomerController {

    private static final String INDEX = "customer";

    private static final String TYPE = "customers";

    private static final String URI = "http://localhost:8080/customer/";

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("")
    public String save(@RequestBody Customer customer) {
        final String id = customer.getId().toString();
        final String result = this.customerRepository.save(id, INDEX, TYPE, CLUSTER_NAME, URI + id);
        return result;
    }

    @PutMapping("")
    public String update(@RequestBody Customer customer) {
        final String id = customer.getId().toString();
        final String result = this.customerRepository.updateById(id, INDEX, TYPE, CLUSTER_NAME, URI + id);
        return result;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        final String result = this.customerRepository.deleteById(id.toString(), INDEX, TYPE, CLUSTER_NAME);
        return result;
    }

    @GetMapping("/search")
    public Page<JSONObject> search() {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(QueryBuilders.wildcardQuery("name", "*doe"))
                .must(QueryBuilders.rangeQuery("age").from(15).to(25));
        Sort sort = new Sort(Sort.Direction.DESC, "name");
        Pageable pageable = new PageRequest(0, 2, sort);
        final Page<JSONObject> result = this.customerRepository.search(INDEX, queryBuilder, pageable);
        return result;
    }

    @GetMapping("{id}")
    public String findById(@PathVariable Long id) {
        return this.customerRepository.getById(id.toString(), INDEX, TYPE, CLUSTER_NAME);
    }
}
