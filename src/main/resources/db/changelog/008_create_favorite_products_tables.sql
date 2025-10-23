CREATE TABLE "users_favorited_products" (
    "product_id" UUID NOT NULL,
    "user_id" UUID NOT NULL,
    CONSTRAINT "users_favorited_products_to_products_fk" FOREIGN KEY ("product_id")
        REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "users_favorited_products_to_users_fk" FOREIGN KEY ("user_id")
        REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "users_favorited_products_fk" PRIMARY KEY ("product_id", "user_id")
);