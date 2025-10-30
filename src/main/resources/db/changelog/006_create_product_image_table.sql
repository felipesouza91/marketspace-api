-- CreateTable
CREATE TABLE "products_images" (
    "product_id" UUID NOT NULL,
    "file_id" UUID NOT NULL,
    CONSTRAINT "products_images_pk" PRIMARY KEY ("product_id", "file_id"),
    CONSTRAINT "products_images_product_id_fk" FOREIGN KEY ("product_id")
        REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "products_images_file_id_fk" FOREIGN KEY ("file_id")
        REFERENCES "files" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);