package com.carloswimmer.todolist.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.Data;

public class ObjectMergerTest {

    // Classe simples para teste (POJO)
    @Data
    static class TestObject {
        private String name;
        private Integer age;
        private String city;
    }

    @Test
    @DisplayName("Deve mesclar apenas propriedades não nulas")
    void shouldMergeOnlyNonNullProperties() {
        // ARRANGE
        TestObject target = new TestObject();
        target.setName("Original Name");
        target.setAge(25);
        target.setCity("New York");

        TestObject source = new TestObject();
        source.setName("Updated Name");
        // age e city são nulos no source

        // ACT
        ObjectMerger.merge(source, target);

        // ASSERT
        assertThat(target.getName()).isEqualTo("Updated Name"); // Mudou
        assertThat(target.getAge()).isEqualTo(25); // Permaneceu o original
        assertThat(target.getCity()).isEqualTo("New York"); // Permaneceu o original
    }

    @Test
    @DisplayName("Deve lançar exceção quando o source for nulo")
    void shouldThrowExceptionWhenSourceIsNull() {
        TestObject target = new TestObject();

        assertThatThrownBy(() -> ObjectMerger.merge(null, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Source and target cannot be null");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o target for nulo")
    void shouldThrowExceptionWhenTargetIsNull() {
        TestObject source = new TestObject();

        assertThatThrownBy(() -> ObjectMerger.merge(source, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Source and target cannot be null");
    }

    @Test
    @DisplayName("Deve funcionar corretamente quando todas as propriedades estão preenchidas")
    void shouldMergeAllPropertiesWhenNoneAreNull() {
        // ARRANGE
        TestObject target = new TestObject();
        target.setName("Old");
        target.setAge(10);

        TestObject source = new TestObject();
        source.setName("New");
        source.setAge(20);
        source.setCity("Miami");

        // ACT
        ObjectMerger.merge(source, target);

        // ASSERT
        assertThat(target.getName()).isEqualTo("New");
        assertThat(target.getAge()).isEqualTo(20);
        assertThat(target.getCity()).isEqualTo("Miami");
    }
}