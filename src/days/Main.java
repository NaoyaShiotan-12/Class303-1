package days;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Main {
	public static void main(String[] args) {
		//1-1. JFrame (窓)を作成
		JFrame frame = new JFrame("デイリープランナー");

		//1-2.閉じるボタンを押したときにプログラムを終了させる設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//1-3.サイズ（幅450，高さ400）を決める
		frame.setSize(450, 400);

		//1-4.レイアウト　（部品の並び方）を「東西南北」方式に設定
		frame.setLayout(new BorderLayout());

		//2-1.	部品をまとめるパネル（トレイのようなもの）を作る
		JPanel inputPanel = new JPanel();

		//2-2.文字を入力する欄を作る（数字はマスの幅）
		JTextField hourField = new JTextField(5); //時間用
		JTextField minuteField = new JTextField(5);

		JTextField taskField = new JTextField(15); // 予定用

		//2-3.実行ボタンを作る
		JButton addButton = new JButton("追加");

		//2-4.パネルにラベルと入力欄、ボタンを順番に乗せる
		inputPanel.add(hourField);
		inputPanel.add(new JLabel("時"));
		inputPanel.add(minuteField);
		inputPanel.add(new JLabel("分"));
		inputPanel.add(taskField);
		inputPanel.add(addButton);

		//2-5.最後に、このパネルをフレームの「上に」配置する
		frame.add(inputPanel, BorderLayout.NORTH);

		//3-1.リストの「中身（データ)」を管理するモデルを作る
		//これにデータを追加すると、画面の表示も自動で変わります
		DefaultListModel<String> listModel = new DefaultListModel<>();

		// 3-2.リストを表示する「棚」を作る(上とセット)
		JList<String> taskList = new JList<>(listModel);

		//3-3.スクロールできるように「スクロールパネル」に入れる
		// 予定が増えても、これがあれば上下に動かせる
		JScrollPane scrollPane = new JScrollPane(taskList);

		//3-4.フレームの「中央」に配置する
		frame.add(scrollPane, BorderLayout.CENTER);

		//4-1.「追加」ボタンが押された時の処理（アクションリスナー）を登録
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//4-2.　入力欄(TextField)から文字を抜き出す
				String hour = hourField.getText();
				String minute = minuteField.getText();
				String task = taskField.getText();

				//4-3.入力チェックとリストへの追加
				if (!hour.isEmpty() && !minute.isEmpty() && !task.isEmpty()) {

					//　時　分　予定　という形式でリストに追加
					listModel.addElement(hour + "時" + minute + "分-" + task);

					//入力欄をクリア
					hourField.setText("");
					minuteField.setText("");
					taskField.setText("");
				} else {
					JOptionPane.showMessageDialog(frame, "すべて入力してください");
				}

			}

		});//ここでActionListenerを閉じる

		//5-1.削除ボタンを作成
		JButton deleteButton = new JButton("洗濯した予定を削除");

		//5-2.ボタンをフレームの下に配置
		frame.add(deleteButton, BorderLayout.SOUTH);

		//5-3.削除ボタンが押された時の処理
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//現在リストで選択されている行の番号（インデックス）取得
				int selectedIndex = taskList.getSelectedIndex();

				//もし何かが選択されていたら（選択されていないときは-1になる）
				if (selectedIndex != -1) {
					//データモデルからその行を削除
					listModel.remove(selectedIndex);
				} else {
					//何も選んでいない状態で押されたら案内を出す
					JOptionPane.showMessageDialog(frame, "削除する予定を選んでください");
				}
			}
		});

		//1-5.最後に「表示しろ！」と命令する（これがないと映りません）
		frame.setVisible(true);

	}
}
