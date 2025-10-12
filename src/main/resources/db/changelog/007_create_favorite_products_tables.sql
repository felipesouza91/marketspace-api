CREATE TABLE "users_favorited_products" (
    "product_id" UUID NOT NULL PRIMARY_KEY,
    "user_id" UUID NOT NULL PRIMARY_KEY,
    CONSTRAINT "_favorited_products_A_fkey" FOREIGN KEY ("product_id") REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "_favorited_products_B_fkey" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);