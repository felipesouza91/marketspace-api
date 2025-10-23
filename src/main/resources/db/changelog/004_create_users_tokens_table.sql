-- CreateTable
CREATE TABLE "users_tokens" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "expires_in" INTEGER NOT NULL,
    "user_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "users_tokens_users_id_fk" FOREIGN KEY ("user_id")
        REFERENCES "users" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);
