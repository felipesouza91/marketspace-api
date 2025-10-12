CREATE TABLE "users_favorited_products" (
    "product_id" UUID NOT NULL,
    "user_id" UUID NOT NULL,
    CONSTRAINT "_favorited_products_A_fkey" FOREIGN KEY ("product_id")
        REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "_favorited_products_B_fkey" FOREIGN KEY ("user_id")
        REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "users_favorited_products_pk" PRIMARY KEY ("product_id", "user_id")
);