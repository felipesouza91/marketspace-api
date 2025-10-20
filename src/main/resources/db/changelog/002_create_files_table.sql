-- CreateTable
CREATE TABLE "files" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "path" VARCHAR(255) NOT NULL,
    "file_name" VARCHAR(255) NOT NULL,
    "original_file_name" VARCHAR(255) NOT NULL,
    "content_type" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
