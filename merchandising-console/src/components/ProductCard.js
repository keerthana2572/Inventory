import React from 'react';

const ProductCard = ({ 
  product, 
  onAcceptPricing, 
  onRejectPricing, 
  onAcceptReorder, 
  onRejectReorder,
  onSimulateSale,
  onSimulateRestock
}) => {
  const getStatusBadge = (status) => {
    switch (status) {
      case 'PRICE_REVIEW_PENDING':
        return <span className="badge price-review">PRICE REVIEW</span>;
      default:
        return null;
    }
  };

  const getAlertBadges = (alerts) => {
    return alerts.map((alert, index) => {
      switch (alert) {
        case 'INVENTORY_LOW':
          return <span key={index} className="badge low-inventory">LOW STOCK</span>;
        case 'DEMAND_SPIKE':
          return <span key={index} className="badge demand-spike">HIGH DEMAND</span>;
        default:
          return <span key={index} className="badge manual">{alert}</span>;
      }
    });
  };

  return (
    <div className={`product-card ${product.status === 'PRICE_REVIEW_PENDING' ? 'pending' : ''} ${product.alerts.includes('INVENTORY_LOW') ? 'low-inventory' : ''} ${product.alerts.includes('DEMAND_SPIKE') ? 'demand-spike' : ''}`}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
        <div>
          <h3>{product.name}</h3>
          <p style={{ color: '#666', fontSize: '0.9em' }}>SKU: {product.sku}</p>
        </div>
        <div>
          {getStatusBadge(product.status)}
          {getAlertBadges(product.alerts)}
        </div>
      </div>

      <div style={{ marginTop: '10px', display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
        <div>
          <strong>Stock Level:</strong> {product.stockLevel}
        </div>
        <div>
          <strong>Current Price:</strong> ${product.currentPrice}
        </div>
        <div>
          <strong>Demand Velocity:</strong> {product.demandVelocity}/day
        </div>
        <div>
          <strong>Status:</strong> {product.status.replace('_', ' ')}
        </div>
      </div>

      {product.pricingSuggestion && (
        <div className="suggestion">
          <h4>Pricing Suggestion</h4>
          <p><strong>Suggested Price:</strong> ${product.pricingSuggestion.suggestedPrice}</p>
          <p><strong>Confidence:</strong> {(product.pricingSuggestion.confidence * 100).toFixed(0)}%</p>
          <p><strong>Reasoning:</strong> {product.pricingSuggestion.reasoning}</p>
          <div className="button-group">
            <button 
              className="btn accept" 
              onClick={() => onAcceptPricing(product.id)}
            >
              Accept
            </button>
            <button 
              className="btn reject" 
              onClick={() => onRejectPricing(product.id)}
            >
              Reject
            </button>
          </div>
        </div>
      )}

      {product.reorderSuggestion && (
        <div className="suggestion">
          <h4>Reorder Suggestion</h4>
          <p><strong>Suggested Quantity:</strong> {product.reorderSuggestion.suggestedQuantity}</p>
          <p><strong>Confidence:</strong> {(product.reorderSuggestion.confidence * 100).toFixed(0)}%</p>
          <p><strong>Reasoning:</strong> {product.reorderSuggestion.reasoning}</p>
          <div className="button-group">
            <button 
              className="btn accept" 
              onClick={() => onAcceptReorder(product.id)}
            >
              Accept
            </button>
            <button 
              className="btn reject" 
              onClick={() => onRejectReorder(product.id)}
            >
              Reject
            </button>
          </div>
        </div>
      )}

      <div className="button-group">
        <button 
          className="btn simulate" 
          onClick={() => onSimulateSale(product.id)}
        >
          Simulate Sale
        </button>
        <button 
          className="btn simulate" 
          onClick={() => onSimulateRestock(product.id)}
        >
          Simulate Restock
        </button>
      </div>
    </div>
  );
};

export default ProductCard;