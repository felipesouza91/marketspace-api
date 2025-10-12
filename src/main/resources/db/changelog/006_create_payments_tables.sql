
-- CreateTable
CREATE TABLE "payments_methods" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "key" TEXT NOT NULL,
    "name" TEXT NOT NULL
);

-- CreateTable
CREATE TABLE "payments_methods_to_products" (
    "payment_methods_id" TEXT NOT NULL ,
    "product_id" UUID NOT NULL ,
    CONSTRAINT "payments_methods_to_products_A_fkey" FOREIGN KEY ("payment_methods_id")
        REFERENCES "payments_methods" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "payments_methods_to_products_B_fkey" FOREIGN KEY ("product_id")
        REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "payments_methods_to_products_pk" PRIMARY KEY ("payment_methods_id", "product_id")
);

