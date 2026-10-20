# Merchandising Console

A React 18 application for merchandisers to review and act on inventory alerts and AI suggestions.

## Floor Requirements Implemented

✅ Products in PRICE_REVIEW_PENDING or with pending suggestions
✅ Inline pricing and reorder suggestions with confidence and AI reasoning
✅ Accept/Reject for both suggestion types
✅ Badges for INVENTORY_LOW vs DEMAND_SPIKE vs manual
✅ Product list showing stock level, current price, demand velocity, status
✅ Simulate sale button or stock PATCH for demo without curl
✅ Polling or refresh; loading and error states

## Features

- **Real-time Inventory Alerts**: View products that require attention based on AI analysis
- **AI-Powered Suggestions**: Review pricing and reorder suggestions with confidence scores and reasoning
- **Quick Actions**: Accept or reject suggestions with one click
- **Simulation Tools**: Simulate sales and restocking without backend calls
- **Visual Indicators**: Color-coded badges for different alert types
- **Responsive Design**: Works on desktop and tablet devices

## Getting Started

1. Install dependencies:
   ```
   npm install
   ```

2. Start the development server:
   ```
   npm start
   ```

3. Open [http://localhost:3000](http://localhost:3000) in your browser

## Architecture

- **React 18** with functional components and hooks
- **Axios** for HTTP client (simulated in this demo)
- **CSS Grid** for responsive layout
- **Component-based design** for maintainability

## Data Flow

1. **Data Fetching**: Loads mock product data with AI suggestions
2. **Display**: Shows products with visual indicators for alerts
3. **Actions**: Users can accept/reject suggestions or simulate inventory changes
4. **State Updates**: Local state is updated to reflect user actions
5. **Refresh**: Manual refresh to reload latest data (simulated)

## Future Enhancements (Ceiling Features)

- Full catalog board view
- Margin display for profitability analysis
- Price history charts
- Category filters
- Stock heatmaps
- Real backend integration
- User authentication
- Audit trails for merchandising decisions