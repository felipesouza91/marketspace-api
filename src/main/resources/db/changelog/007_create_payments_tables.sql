
-- CreateTable
CREATE TABLE "payments_methods" (
    "id" UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    "key" TEXT NOT NULL,
    "name" TEXT NOT NULL
);

INSERT INTO payments_methods(KEY,NAME)
    VALUES('boleto', 'Boleto');

INSERT INTO payments_methods(KEY,NAME)
    VALUES('pix', 'Pix');

INSERT INTO payments_methods(KEY,NAME)
    VALUES('cash', 'Dinheiro');

INSERT INTO payments_methods(KEY,NAME)
    VALUES('card', 'Cartão de Crédito');

INSERT INTO payments_methods(KEY,NAME)
    VALUES('deposit', 'Depósito Bancário');

-- CreateTable
CREATE TABLE "payments_methods_to_products" (
    "payment_methods_id" UUID NOT NULL ,
    "product_id" UUID NOT NULL ,
    CONSTRAINT "payments_methods_to_payments_methods_fk" FOREIGN KEY ("payment_methods_id")
        REFERENCES "payments_methods" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "payments_methods_to_products_products_fk" FOREIGN KEY ("product_id")
        REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "payments_methods_to_products_pk" PRIMARY KEY ("payment_methods_id", "product_id")
);

