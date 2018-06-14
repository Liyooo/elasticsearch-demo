package com.example.elasticsearchDemo.model;

import java.io.Serializable;

/**
 * Customer.
 *
 * @author gao jx
 */
public class Customer implements Serializable {
    private Long id;
    private String name;
    private Integer age;

    public final Long getId() {
        return id;
    }

    public final void setId(final Long id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final Integer getAge() {
        return age;
    }

    public final void setAge(final Integer age) {
        this.age = age;
    }
}
