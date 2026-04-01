package days;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Main {
    private static DefaultListModel<String> listModel = new DefaultListModel<>();

    public static void main(String[] args) {
        // ウィンドウの設定
        JFrame frame = new JFrame("マイ・デイリープランナー");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // 入力エリア
        JPanel inputPanel = new JPanel();
        JTextField timeField = new JTextField(5); // 時間入力
        JTextField taskField = new JTextField(15); // 予定入力
        JButton addButton = new JButton("追加");
        
        inputPanel.add(new JLabel("時間:"));
        inputPanel.add(timeField);
        inputPanel.add(new JLabel("予定:"));
        inputPanel.add(taskField);
        inputPanel.add(addButton);

        // リスト表示エリア
        JList<String> taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // 削除ボタン
        JButton deleteButton = new JButton("選択した予定を削除");

        // 追加ボタンの動作
        addButton.addActionListener(e -> {
            String time = timeField.getText();
            String task = taskField.getText();
            if (!time.isEmpty() && !task.isEmpty()) {
                listModel.addElement(time + " - " + task);
                timeField.setText("");
                taskField.setText("");
            }
        });

        // 削除ボタンの動作
        deleteButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });

        // レイアウトに配置
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(deleteButton, BorderLayout.SOUTH);

        // 表示
        frame.setVisible(true);
    }
}
