package br.app.fsantana.marketspaceapi.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

	private Set<String> types;

	@Override
	public void initialize(FileType constraintAnnotation) {
		types = Set.of(constraintAnnotation.types());
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		int size = types.stream()
				.filter(item -> value.getContentType().toLowerCase().contains(item.toLowerCase()))
				.toList().size();
        return size > 0;
    }
}