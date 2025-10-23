package br.app.fsantana.marketspaceapi.utils.mappers;

import br.app.fsantana.marketspaceapi.api.responses.FileResponse;
import org.mapstruct.Mapper;

/**
 * Created by felip on 17/10/2025.
 */

@Mapper
public interface FileMapper {

    FileResponse toResponse(String fileUrl);

}
