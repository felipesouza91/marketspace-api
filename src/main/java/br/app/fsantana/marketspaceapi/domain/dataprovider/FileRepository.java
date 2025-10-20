package br.app.fsantana.marketspaceapi.domain.dataprovider;

import br.app.fsantana.marketspaceapi.domain.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by felip on 20/10/2025.
 */

public interface FileRepository extends JpaRepository<File, UUID> {
}
