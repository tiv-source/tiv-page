function formatCurrency(cellvalue, options, rowObject) {
  var outputCurrency = parseFloat(cellvalue).toFixed(2);
  return outputCurrency + " €";  
}