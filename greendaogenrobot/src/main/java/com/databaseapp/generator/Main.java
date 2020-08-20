package com.databaseapp.generator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Index;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class Main {
    public static void main(String[] args) {
        try {
            Schema schema = new Schema(3, "com.smile.studio.recipe.model.greendao");
            schema.enableKeepSectionsByDefault();
            Entity pecipe = schema.addEntity("Pecipe");
            pecipe.addIdProperty().primaryKey().autoincrement();
            Property firstIdForTablePecipePK = pecipe.addLongProperty("pid").codeBeforeField("@SerializedName(\"id\")").notNull().getProperty();
            Index indexUnique = new Index();
            indexUnique.addProperty(firstIdForTablePecipePK);
            indexUnique.makeUnique();
            pecipe.addIndex(indexUnique);
            pecipe.addStringProperty("title").codeBeforeField("@SerializedName(\"title\")");
            pecipe.addStringProperty("image").codeBeforeField("@SerializedName(\"image\")");
            pecipe.addStringProperty("description").codeBeforeField("@SerializedName(\"description\")");
            pecipe.addStringProperty("content").codeBeforeField("@SerializedName(\"content\")");
            pecipe.addIntProperty("type").codeBeforeField("@SerializedName(\"type\")");

            Entity type = schema.addEntity("Type");

            type.addIdProperty().primaryKey().autoincrement();
            Property firstIdForTableTypePK = type.addIntProperty("pid").codeBeforeField("@SerializedName(\"id\")").notNull().getProperty();
            indexUnique = new Index();
            indexUnique.addProperty(firstIdForTableTypePK);
            indexUnique.makeUnique();
            type.addIndex(indexUnique);
            type.addStringProperty("title").codeBeforeField("@SerializedName(\"title\")");
            new DaoGenerator().generateAll(schema, "../recipe-android/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
