import mongoose from "mongoose";

const uri = "mongodb://localhost:27017/timer_quiz";

mongoose.connect(uri);

const db = mongoose.connection;

db.once("open", () => {
    console.log("✅ Database conectado:", uri);
});

db.on("error", (error) => {
    console.error("❌ Error de conexión:", error);
});

export default db;
