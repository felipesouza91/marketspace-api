/*
  Warnings:

  - You are about to drop the column `payment_methods` on the `Products` table. All the data in the column will be lost.

*/
-- CreateTable
CREATE TABLE "payments_methods" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "key" TEXT NOT NULL,
    "name" TEXT NOT NULL
);

-- CreateTable
CREATE TABLE "payments_methods_to_products" (
    "payment_methods_id" TEXT NOT NULL PRIMARY_KEY,
    "product_id" UUID NOT NULL PRIMARY_KEY,
    CONSTRAINT "payments_methods_to_products_A_fkey" FOREIGN KEY ("payment_methods_id")
        REFERENCES "payments_methods" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "payments_methods_to_products_B_fkey" FOREIGN KEY ("product_id")
        REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);

