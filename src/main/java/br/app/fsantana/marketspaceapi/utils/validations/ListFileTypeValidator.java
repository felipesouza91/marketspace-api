package br.app.fsantana.marketspaceapi.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class ListFileTypeValidator implements ConstraintValidator<FileType, List<MultipartFile>> {

	private Set<String> types;

	@Override
	public void initialize(FileType constraintAnnotation) {
		types = Set.of(constraintAnnotation.types());
	}

	@Override
	public boolean isValid(List<MultipartFile> values, ConstraintValidatorContext context) {
		try {
			values.forEach(item -> {
				int size = types.stream()
						.filter(type -> item.getContentType().toLowerCase().contains(type.toLowerCase()))
						.toList().size();
				if (size < 1 ) {
					throw new ValidationException();
				}
			});
			return true;
		}catch (ValidationException e ) {
			return false;
		}

    }
}