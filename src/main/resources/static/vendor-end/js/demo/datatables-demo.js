$(document).ready(function () {
  // 檢查是否已經存在 DataTable 實例
  if ($.fn.DataTable.isDataTable('#dataTable')) {
    // 如果已存在，先銷毀 DataTable
    $('#dataTable').DataTable().destroy();
  }

  // 初始化 DataTable
  $('#dataTable').DataTable({
    language: {
      "sProcessing": "處理中...",
      "sLengthMenu": "顯示 _MENU_ 項結果",
      "sZeroRecords": "沒有匹配結果",
      "sInfo": "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
      "sInfoEmpty": "顯示第 0 至 0 項結果，共 0 項",
      "sInfoFiltered": "(從 _MAX_ 項結果中過濾)",
      "sInfoPostFix": "",
      "sSearch": "搜尋:",
      "sUrl": "",
      "sEmptyTable": "表中數據為空",
      "sLoadingRecords": "載入中...",
      "sInfoThousands": ",",
      "oPaginate": {
        "sFirst": "首頁",
        "sPrevious": "上一頁",
        "sNext": "下一頁",
        "sLast": "最後一頁"
      },
      "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
      },
      "buttons": {
        "copy": "複製",
        "colvis": "列可見"
      }
    }
  });
});



$('#dataTable').DataTable({
  "lengthMenu": [5, 10, 20, 30, 40, 50]
});