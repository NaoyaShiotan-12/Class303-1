package days;

import java.awt.BorderLayout;
import java.awt.Font;
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
	//12-1.日付(String)をキーにしてその日のリストを保存する地図（Map）
	private static java.util.Map<String, DefaultListModel<String>> allData = new java.util.HashMap<>();

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
		JPanel inputPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		//2-2.文字を入力する欄を作る（数字はマスの幅）
		JTextField hourField = new JTextField(5); //時間用
		JTextField minuteField = new JTextField(5);

		JTextField taskField = new JTextField(15); // 予定用

		//2-3.実行ボタンを作る
		JButton addButton = new JButton("追加");

		//6-1 今日の日付を取得して文字にする（2025/06/20）
		java.time.LocalDate today = java.time.LocalDate.now();
		String dateStr = today.format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		// 日付を表示するラベルを作成（少し太文字でおしゃれに）
		JLabel dateLabel = new JLabel("【" + dateStr + "】");
		dateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

		//2-4.パネルにラベルと入力欄、ボタンを順番に乗せる
		inputPanel.add(dateLabel); //6-2. 最初に追加
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

				DefaultListModel<String> currentModel = (DefaultListModel<String>) taskList.getModel();

				if (currentModel == null) {
					currentModel = new DefaultListModel<>();
					taskList.setModel(currentModel);
				}

				//4-3.入力チェックとリストへの追加
				if (!hour.isEmpty() && !minute.isEmpty() && !task.isEmpty()) {

					//　時　分　予定　という形式でリストに追加

					currentModel.addElement(hour + "時" + minute + "分-" + task);

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
		JButton deleteButton = new JButton("選択した予定を削除");

		//5-2.ボタンをフレームの下に配置
		frame.add(deleteButton, BorderLayout.SOUTH);

		//5-3.削除ボタンが押された時の処理
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (taskList.getModel() instanceof DefaultListModel) {
					DefaultListModel<String> currentModel = (DefaultListModel<String>) taskList.getModel();
					//現在リストで選択されている行の番号（インデックス）取得
					int selectedIndex = taskList.getSelectedIndex();

					//もし何かが選択されていたら（選択されていないときは-1になる）
					if (selectedIndex != -1) {
						//データモデルからその行を削除
						currentModel.remove(selectedIndex);
					} else {
						//何も選んでいない状態で押されたら案内を出す
						JOptionPane.showMessageDialog(frame, "削除する予定を選んでください");

					}
				} else {
					// 日付が選ばれていない場合
					JOptionPane.showMessageDialog(frame, "まずカレンダーの日付をクリックしてください");
				}
			}

		});

		//7-1.カレンダー用のパネル枠を作成
		//GridLayout(行, 列) です。0を指定すると自動で計算してくれます。
		JPanel calendarPanel = new JPanel(new java.awt.GridLayout(0, 7));

		//7-2.曜日ラベルを作成
		String[] days = { "日", "月", "火", "水", "木", "金", "土" };
		for (String d : days) {
			calendarPanel.add(new JLabel(d, JLabel.CENTER));
		}

		//7-3. 1日から３０日までのボタンをループで作成
		for (int i = 1; i <= 30; i++) {
			//11-1.数字を２桁に整えてボタンを作る
			String dayStr = (i < 10) ? "0" + i : String.valueOf(i);
			JButton dayButton = new JButton(dayStr);
			calendarPanel.add(dayButton);

			//10-1.ボタンを押した時の動き
			dayButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					//13-1. 押された日付を取得

					String selectedDay = ((JButton) e.getSource()).getText();
					String fullDate = "2026/04/" + selectedDay;

					//13-2.下の日付ラベルを書き換える
					//１桁の場合は01，02表示にすること！

					dateLabel.setText("【" + fullDate + "】");

					//13-3.【重要】その日のデータが地図にあるか確認。なければ新しく作成
					if (!allData.containsKey(fullDate)) {
						allData.put(fullDate, new DefaultListModel<String>());
					}

					//13-4.画面のリストセット
					taskList.setModel(allData.get(fullDate));
				}

			});
		}

		//8-1.一番上にカレンダーをスクロールできる箱に配置
		JScrollPane calendarScroll = new JScrollPane(calendarPanel);
		calendarScroll.setPreferredSize(new java.awt.Dimension(450, 250));
		frame.add(calendarScroll, BorderLayout.NORTH);

		//8-2. 真ん中は今のままリスト表示(念のため)
		frame.add(scrollPane, BorderLayout.CENTER);

		//8-3.一番下に入力ボタンと削除ボタンをまとめて配置
		//これらをセットするための新しいパネル
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(inputPanel, BorderLayout.NORTH); //入力欄を上
		southPanel.add(deleteButton, BorderLayout.SOUTH); //削除ボタンを下に

		frame.add(southPanel, BorderLayout.SOUTH);

		//9-1.　画面全体のサイズを、中身が全部入る大きさに設定
		frame.setSize(600, 800);

		//14-1起動時に今日の日付のボタンをプログラムからクリックさせる
		taskList.setModel(new DefaultListModel<>());

		//1-5.最後に「表示しろ！」と命令する（これがないと映りません）
		frame.setVisible(true);

	}

}
