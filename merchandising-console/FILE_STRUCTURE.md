# Merchandising Console File Structure

```
merchandising-console/
├── package.json                  # Project dependencies and scripts
├── README.md                     # Project documentation
├── ADR.md                        # Architectural Decision Record
├── public/
│   └── index.html                # HTML template
└── src/
    ├── index.js                  # Application entry point
    ├── index.css                 # Global styles
    ├── App.js                    # Main App component
    ├── App.css                   # App component styles
    └── components/
        ├── MerchandisingConsole.js  # Main container component
        └── ProductCard.js        # Presentational component for products
```

## Component Relationships

1. **index.js** → **App.js** → **MerchandisingConsole.js** → **ProductCard.js**

2. **Data Flow**:
   - MerchandisingConsole fetches/mock product data
   - Maps products to ProductCard components
   - Passes action handlers down to ProductCard
   - ProductCard displays data and triggers actions

## Key Implementation Details

### MerchandisingConsole.js
- State management for products and UI state
- Mock data simulation
- Action handler implementations
- Filtering logic for products requiring attention
- Refresh/polling mechanism

### ProductCard.js
- Presentational component for individual products
- Visual badge system for alerts
- Display of AI suggestions with confidence scores
- Action buttons with appropriate handlers
- Responsive layout using CSS Grid

## Styling Approach

- Plain CSS without preprocessors
- BEM-like naming conventions
- Responsive grid layout
- Color-coded visual indicators
- Component-scoped styling classes