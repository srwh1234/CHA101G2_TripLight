$(document).ready(function () {
    // 編輯按鈕點擊事件
    $('.btn-edit').click(function () {
        var row = $(this).closest('tr'); // 找到最近的父級 <tr> 元素
        var cells = row.find('td'); // 獲取所有 <td> 元素

        // 獲取各個欄位的值
        var employeeId = $(cells[0]).text();
        var employeeName = $(cells[1]).text();
        var department = $(cells[2]).text();
        var position = $(cells[3]).text();
        var hireDate = $(cells[4]).text();
        var phone = $(cells[5]).text();
        var permission = $(cells[6]).text();

        // 將值填充到表單中
        $('#employeeId').val(employeeId);
        $('#employeeName').val(employeeName);
        $('#department').val(department);
        $('#position').val(position);
        $('#hireDate').val(hireDate);
        $('#phone').val(phone);
        $('#permission').val(permission);

        // 顯示修改資料的模態框
        $('#editModal').modal('show');
    });

    // 確定修改按鈕點擊事件
    $('#btnSave').click(function () {
        // 獲取表單中的值
        var employeeId = $('#employeeId').val();
        var employeeName = $('#employeeName').val();
        var department = $('#department').val();
        var position = $('#position').val();
        var hireDate = $('#hireDate').val();
        var phone = $('#phone').val();
        var permission = $('#permission').val();

        // 做一些修改資料的處理，例如發送 AJAX 請求保存資料

        // 關閉修改資料的模態框
        $('#editModal').modal('hide');

        // 更新表格中的資料
        var row = $('#dataTable').find('td:contains(' + employeeId + ')').closest('tr');
        $(row).find('td:eq(1)').text(employeeName);
        $(row).find('td:eq(2)').text(department);
        $(row).find('td:eq(3)').text(position);
        $(row).find('td:eq(4)').text(hireDate);
        $(row).find('td:eq(5)').text(phone);
        $(row).find('td:eq(6)').text(permission);
    });
});