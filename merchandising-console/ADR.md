# Architectural Decision Record: Merchandising Console Implementation

## Context
We need to build a frontend interface for merchandisers to review and act on inventory alerts and AI-generated suggestions. The interface should clearly show the agentic loop end-to-end, demonstrating how AI suggestions flow to human decision makers and how those decisions are executed.

## Decision
We decided to implement a React 18 application that focuses on functionality over aesthetics, ensuring the agentic loop is clearly visible and actionable. The implementation prioritizes:

1. Showing products with AI suggestions clearly
2. Making the decision-making process transparent
3. Providing immediate feedback on actions
4. Simulating backend interactions for demonstration

## Consequences

### Positive
1. **Clear Agentic Loop**: The interface demonstrates the entire cycle from AI suggestion to human action
2. **Actionable Interface**: Merchandisers can immediately act on suggestions
3. **Transparent Reasoning**: AI reasoning is displayed alongside suggestions
4. **Simulation Capabilities**: Can demonstrate functionality without backend
5. **Responsive Design**: Works across different device sizes
6. **Extensible Architecture**: Component-based design allows for easy extension

### Negative
1. **UI Polish**: Interface is functional but not visually polished
2. **Limited Filtering**: No advanced filtering or sorting capabilities in floor implementation
3. **Mock Data**: Uses simulated data rather than real backend integration

## Implementation Details

### Technology Stack
- **React 18**: Modern React with functional components and hooks
- **JavaScript ES6+**: Modern JavaScript features
- **CSS**: Plain CSS for styling without additional libraries
- **Axios**: HTTP client library (simulated in demo)

### Key Components

#### MerchandisingConsole (Container)
- Manages overall application state
- Fetches and filters product data
- Handles user actions and state updates
- Provides refresh/polling mechanism

#### ProductCard (Presentational)
- Displays individual product information
- Shows alert badges and status indicators
- Renders AI suggestions with confidence scores
- Provides action buttons for user interactions

### State Management
- **Local State**: Uses React's useState hook for managing products and UI state
- **Effect Hooks**: Uses useEffect for initial data loading
- **Immutability**: Updates state immutably to trigger re-renders

### User Interface Features

#### Visual Indicators
- **Color-coded borders**: Different colors for different alert states
- **Badges**: Clear visual indicators for alert types
  - ORANGE: PRICE_REVIEW_PENDING
  - RED: INVENTORY_LOW
  - GREEN: DEMAND_SPIKE
  - BLUE: Manual alerts
- **Suggestion boxes**: Highlighted areas for AI suggestions

#### Action Buttons
- **Accept/Reject**: For pricing and reorder suggestions
- **Simulate Sale/Restock**: For demonstrating inventory changes
- **Refresh**: Manual data refresh

#### Feedback Mechanisms
- **Loading states**: Spinner and messages during data fetching
- **Error states**: Clear error messages and retry options
- **Immediate updates**: UI updates immediately on user actions

### Data Structure
Each product contains:
- Basic info: name, SKU, stock level, price, demand velocity
- Status information: current status and alert badges
- AI suggestions: pricing and reorder with confidence scores
- Reasoning: Natural language explanations for AI suggestions

### Simulation Features
- **Mock Data**: Predefined product data simulates backend responses
- **State Updates**: User actions update local state to simulate backend effects
- **Timed Operations**: Artificial delays simulate network requests
- **No Backend Required**: Entire demo runs without backend services

## Usage Instructions

### Starting the Application
```bash
npm install
npm start
```

### Interacting with the Interface
1. View products with alerts and suggestions
2. Review AI reasoning for each suggestion
3. Accept or reject suggestions using action buttons
4. Simulate sales or restocking to see inventory changes
5. Refresh data to reset the simulation

## Testing Strategy
- **Manual Testing**: Verify UI elements and interactions
- **State Transitions**: Ensure state updates correctly on user actions
- **Edge Cases**: Test with empty data and error conditions
- **Responsiveness**: Check layout on different screen sizes

## Future Considerations
1. **Backend Integration**: Connect to real inventory management APIs
2. **Advanced Filtering**: Add category, price range, and alert type filters
3. **User Authentication**: Secure access for merchandising teams
4. **Audit Trail**: Log all merchandising decisions for analysis
5. **Performance Optimization**: Virtual scrolling for large product catalogs
6. **Internationalization**: Support for multiple languages

## Status
Accepted

## Authors
Frontend Development Team

## Date
October 20, 2026