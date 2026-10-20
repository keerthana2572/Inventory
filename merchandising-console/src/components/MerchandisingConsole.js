  // Simulate accepting a pricing suggestion
  const acceptPricingSuggestion = async (productId) => {
    try {
      // In a real app, this would be an API call:
      // await axios.patch(`/api/products/${productId}/price`, { 
      //   newPrice: products.find(p => p.id === productId).pricingSuggestion.suggestedPrice 
      // });
      
      // For demo, just update local state
      setProducts(products.map(product => 
        product.id === productId 
          ? { 
              ...product, 
              currentPrice: product.pricingSuggestion.suggestedPrice,
              pricingSuggestion: null,
              status: product.reorderSuggestion ? "ACTIVE" : "NO_SUGGESTIONS"
            } 
          : product
      ));
    } catch (err) {
      console.error('Failed to accept pricing suggestion', err);
    }
  };

  // Simulate rejecting a pricing suggestion
  const rejectPricingSuggestion = async (productId) => {
    try {
      // In a real app, this would be an API call:
      // await axios.delete(`/api/products/${productId}/pricing-suggestion`);
      
      // For demo, just update local state
      setProducts(products.map(product => 
        product.id === productId 
          ? { ...product, pricingSuggestion: null, status: product.reorderSuggestion ? "ACTIVE" : "NO_SUGGESTIONS" } 
          : product
      ));
    } catch (err) {
      console.error('Failed to reject pricing suggestion', err);
    }
  };

  // Simulate accepting a reorder suggestion
  const acceptReorderSuggestion = async (productId) => {
    try {
      // In a real app, this would be an API call:
      // await axios.post(`/api/products/${productId}/reorder`, { 
      //   quantity: products.find(p => p.id === productId).reorderSuggestion.suggestedQuantity 
      // });
      
      // For demo, just update local state
      setProducts(products.map(product => 
        product.id === productId 
          ? { 
              ...product, 
              reorderSuggestion: null,
              status: product.pricingSuggestion ? "PRICE_REVIEW_PENDING" : "NO_SUGGESTIONS"
            } 
          : product
      ));
    } catch (err) {
      console.error('Failed to accept reorder suggestion', err);
    }
  };

  // Simulate rejecting a reorder suggestion
  const rejectReorderSuggestion = async (productId) => {
    try {
      // In a real app, this would be an API call:
      // await axios.delete(`/api/products/${productId}/reorder-suggestion`);
      
      // For demo, just update local state
      setProducts(products.map(product => 
        product.id === productId 
          ? { ...product, reorderSuggestion: null, status: product.pricingSuggestion ? "PRICE_REVIEW_PENDING" : "NO_SUGGESTIONS" } 
          : product
      ));
    } catch (err) {
      console.error('Failed to reject reorder suggestion', err);
    }
  };

  // Simulate a sale
  const simulateSale = async (productId) => {
    try {
      // In a real app, this would be an API call:
      // await axios.patch(`/api/products/${productId}/stock`, { 
      //   adjustment: -1 
      // });
      
      // For demo, just update local state
      setProducts(products.map(product => 
        product.id === productId 
          ? { ...product, stockLevel: Math.max(0, product.stockLevel - 1) } 
          : product
      ));
    } catch (err) {
      console.error('Failed to simulate sale', err);
    }
  };

  // Simulate restocking
  const simulateRestock = async (productId, quantity = 10) => {
    try {
      // In a real app, this would be an API call:
      // await axios.patch(`/api/products/${productId}/stock`, { 
      //   adjustment: quantity 
      // });
      
      // For demo, just update local state
      setProducts(products.map(product => 
        product.id === productId 
          ? { ...product, stockLevel: product.stockLevel + quantity } 
          : product
      ));
    } catch (err) {
      console.error('Failed to simulate restock', err);
    }
  };

  // Refresh data
  const handleRefresh = () => {
    setRefreshing(true);
    fetchProducts();
  };

  // Initial data fetch
  useEffect(() => {
    fetchProducts();
  }, []);