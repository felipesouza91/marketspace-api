-- CreateTable
CREATE TABLE "products_images" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "path" VARCHAR(255) NOT NULL,
    "product_id" UUID NOT NULL,
    "content_type" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "ProductImages_product_id_fkey" FOREIGN KEY ("product_id") REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);