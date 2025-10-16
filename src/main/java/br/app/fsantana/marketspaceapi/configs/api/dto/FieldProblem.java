package br.app.fsantana.marketspaceapi.configs.api.dto;

import lombok.Builder;

@Builder
public record FieldProblem(String name, String detail) {

}