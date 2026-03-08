const form = document.getElementById("booking-form");
const eventTypeDropdown = document.getElementById("event-type");

// Global selection object for services
const selectedServices = {
    food: [],
    decoration: [],
    photography: [],
    sound: []
};

// Pricing Dictionary (in ₹)
const PRICING = {
    food: {
        "Non-Veg Biryani": 450, // Per guest
        "Veg Biryani": 350, // Per guest
        "Starters": 250, // Per guest
        "Main Course": 500, // Per guest
        "Side Courses": 150, // Per guest
        "Ice Creams": 100, // Per guest
        "Gulab Jamun": 80, // Per guest
        "Fruit Salads": 120, // Per guest
        "Other Sweets": 150 // Per guest
    },
    decoration: {
        "Wedding Decor": 50000,
        "Haldi Decor": 15000,
        "Party Decor": 10000
    },
    photography: {
        "Wedding Photography": 25000,
        "Event Photography": 10000,
        "Candid Photography": 15000
    },
    sound: {
        "DJ Setup": 15000,
        "Stage Lighting": 8000,
        "Concert Sound": 30000
    }
};

// Auto-fill event type if coming from an event details page
document.addEventListener("DOMContentLoaded", () => {
    const selectedEvent = sessionStorage.getItem('selectedEvent');
    if (selectedEvent && eventTypeDropdown) {
        eventTypeDropdown.value = selectedEvent;
        sessionStorage.removeItem('selectedEvent');

        // Auto-scroll to the booking section
        const bookingSection = document.getElementById('contact');
        if (bookingSection) {
            setTimeout(() => {
                bookingSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }, 100);
        }
    }

    // Also handle URL query parameters as backup (e.g. ?type=Birthday Functions)
    const urlParams = new URLSearchParams(window.location.search);
    const typeQuery = urlParams.get('type');
    if (typeQuery && eventTypeDropdown) {
        eventTypeDropdown.value = typeQuery;
    }
});

function nextStep(step) {
    if (step === 2) {
        // Validate Step 1 before proceeding
        const name = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const eventType = document.getElementById("event-type").value;
        const date = document.getElementById("date").value;
        
        if (!name || !email || !eventType || !date) {
            // Using browser validation via reportValidity if possible
            if(form && typeof form.reportValidity === 'function') {
                form.reportValidity();
            } else {
                alert("Please fill in Name, Email, Event Type, and Date.");
            }
            return;
        }
    }

    document.querySelectorAll('.booking-step').forEach(el => {
        el.classList.remove('active');
    });
    document.getElementById('booking-step-' + step).classList.add('active');
}

function toggleSelection(element) {
    const category = element.getAttribute('data-category');
    const value = element.getAttribute('data-value');
    
    element.classList.toggle('selected');
    const btn = element.querySelector('.card-btn');
    
    if (element.classList.contains('selected')) {
        btn.innerText = 'Selected';
        if (!selectedServices[category].includes(value)) {
            selectedServices[category].push(value);
        }
    } else {
        btn.innerText = 'Select';
        selectedServices[category] = selectedServices[category].filter(item => item !== value);
    }
}

function toggleSubCategory(id, element) {
    const subContainer = document.getElementById(id);
    const btn = element.querySelector('.toggle-btn');
    
    // Toggle active state for styling
    element.classList.toggle('expanded');
    
    if (subContainer.style.display === "none") {
        subContainer.style.display = "block";
        if (btn) btn.innerText = 'Hide Options';
    } else {
        subContainer.style.display = "none";
        if (btn) btn.innerText = 'View Options';
    }
}

function generateSummary() {
    const summaryBox = document.getElementById("booking-summary");
    if (!summaryBox) return;
    
    const eventType = document.getElementById("event-type").value;
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const date = document.getElementById("date").value;
    const rawGuests = parseInt(document.getElementById("guests").value);
    const guests = (rawGuests && rawGuests > 0) ? rawGuests : 50; // Minimum 50 guests

    const formatCurrency = (num) => '₹' + num.toLocaleString('en-IN');

    // Calculate Prices - itemized
    let foodPrice = 0;
    let foodItemsHTML = '';
    selectedServices.food.forEach(item => {
        const perHead = PRICING.food[item] || 0;
        const total = perHead * guests;
        foodPrice += total;
        foodItemsHTML += `<li>${item} &times; ${guests} guests @ ${formatCurrency(perHead)}/head = <strong>${formatCurrency(total)}</strong></li>`;
    });

    let decorPrice = 0;
    let decorItemsHTML = '';
    selectedServices.decoration.forEach(item => {
        const price = PRICING.decoration[item] || 0;
        decorPrice += price;
        decorItemsHTML += `<li>${item} = <strong>${formatCurrency(price)}</strong></li>`;
    });

    let photoPrice = 0;
    let photoItemsHTML = '';
    selectedServices.photography.forEach(item => {
        const price = PRICING.photography[item] || 0;
        photoPrice += price;
        photoItemsHTML += `<li>${item} = <strong>${formatCurrency(price)}</strong></li>`;
    });

    let soundPrice = 0;
    let soundItemsHTML = '';
    selectedServices.sound.forEach(item => {
        const price = PRICING.sound[item] || 0;
        soundPrice += price;
        soundItemsHTML += `<li>${item} = <strong>${formatCurrency(price)}</strong></li>`;
    });

    const totalPrice = foodPrice + decorPrice + photoPrice + soundPrice;
    window.currentTotalPrice = totalPrice;

    const renderCategory = (label, itemsHTML, price) => {
        if (!itemsHTML) return `<p><strong>${label}:</strong> None</p>`;
        return `
        <div class="summary-category">
            <p><strong>${label}:</strong></p>
            <ul class="summary-item-list">${itemsHTML}</ul>
            <p class="summary-subtotal">Subtotal: <span>${formatCurrency(price)}</span></p>
        </div>`;
    };

    let summaryHTML = `
        <p><strong>Customer Name:</strong> ${name || 'N/A'}</p>
        <p><strong>Email:</strong> ${email || 'N/A'}</p>
        <p><strong>Event Type:</strong> ${eventType || 'N/A'}</p>
        <p><strong>Event Date:</strong> ${date || 'N/A'}</p>
        <p><strong>Number of Guests:</strong> ${guests}</p>
        <hr style="margin: 15px 0; border: 0; border-top: 1px solid #ddd;">
        ${renderCategory('Food', foodItemsHTML, foodPrice)}
        ${renderCategory('Decoration', decorItemsHTML, decorPrice)}
        ${renderCategory('Photography', photoItemsHTML, photoPrice)}
        ${renderCategory('Sound & Lighting', soundItemsHTML, soundPrice)}
        <hr style="margin: 15px 0; border: 0; border-top: 2px solid #333;">
        <h4 style="text-align: right; color: #ff5722;">Total Estimated Price: ${formatCurrency(totalPrice)}</h4>
    `;
    
    summaryBox.innerHTML = summaryHTML;
}

if (form) {
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const data = {
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            eventType: document.getElementById("event-type").value,
            date: document.getElementById("date").value,
            guests: document.getElementById("guests").value,
            location: document.getElementById("location").value,
            budget: typeof window.currentTotalPrice !== 'undefined' ? window.currentTotalPrice : 0, // Using generated price instead of user budget
            details: document.getElementById("details").value,
            foodSelections: selectedServices.food,
            decorationSelections: selectedServices.decoration,
            photographySelections: selectedServices.photography,
            soundSelections: selectedServices.sound
        };

        try {
            const response = await fetch("http://localhost:3000/book-event", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();
            alert(result.message);
            form.reset();
            
            // Reset Selections
            document.querySelectorAll('.service-option-card').forEach(card => {
                card.classList.remove('selected');
                card.classList.remove('expanded');
                const btn = card.querySelector('.card-btn');
                if (btn && !btn.classList.contains('toggle-btn')) {
                    btn.innerText = 'Select';
                } else if (btn && btn.classList.contains('toggle-btn')) {
                    btn.innerText = 'View Options';
                }
            });
            document.querySelectorAll('.sub-options-container').forEach(container => {
                container.style.display = 'none';
            });
            Object.keys(selectedServices).forEach(key => selectedServices[key] = []);
            
            // Go back to Step 1
            nextStep(1);
            
        } catch (error) {
            console.error("Error booking event:", error);
            alert("Error booking event. Is the server running?");
        }
    });
}

