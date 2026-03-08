const express = require("express");
const cors = require("cors");

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());

// Serve static files from the current directory
app.use(express.static(__dirname));

// Route to handle booking
app.post("/book-event", (req, res) => {
    const { name, email, eventType, guests, location, budget, date, details, foodSelections, decorationSelections, photographySelections, soundSelections } = req.body;

    console.log("===============================");
    console.log("New Event Booking Received:");
    console.log("Name:", name);
    console.log("Email:", email);
    console.log("Event Type:", eventType);
    console.log("Date:", date);
    console.log("Guests:", guests);
    console.log("Location:", location);
    console.log("Total Estimated Price (₹):", budget);
    console.log("Details:", details);
    console.log("--- Services Selected ---");
    console.log("Food:", foodSelections ? foodSelections.join(', ') : "None");
    console.log("Decoration:", decorationSelections ? decorationSelections.join(', ') : "None");
    console.log("Photography:", photographySelections ? photographySelections.join(', ') : "None");
    console.log("Sound & Lighting:", soundSelections ? soundSelections.join(', ') : "None");
    console.log("===============================");

    res.json({
        message: "Event booked successfully! We will contact you soon."
    });
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
