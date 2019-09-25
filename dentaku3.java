import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class dentaku3 extends JFrame {
    /*↓計算結果画面の大きさを調整*/
    JTextField total = new JTextField("",30);
    double stackedValue = 0.0;
    boolean isStacked = false;
    boolean afterCalc = false;
    String currentOp = "";


    public static void main(String args[]){
        dentaku3 frame = new dentaku3("けいさんき");
        frame.setSize(500,500);
        /*↓画面を出力する*/
        frame.setVisible(true);
    }


    dentaku3(String title){
        setTitle(title);

        /*↓数字画面の大きさ(前半２つが画面が現れる場所を指定している)
        1番目の引数と2番目の引数でアプリケーションが表示される座標を指定します。
        そして3番目と4番目の引数で幅と高さを指定します。いずれも単位はピクセルとなります。*/
        setBounds(500, 500, 500, 500);
        /*↓ウインドウをどう閉じるかを設定している*/
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*JPanelを使ってボタンや結果を表示している*/
        /*JPanel pは計算結果を表す欄*/
        JPanel p = new JPanel();

        JPanel key = new JPanel();
        key.setLayout(new GridLayout(5,1));

        JPanel keisan = new JPanel();
        //JPanel panel = new JPanel();

        p.add(total);
        /*インスタンス化して表示したい番号(値)を引数に入れる*/
        JButton button7 = new NumberButton("7");
        JButton button8 = new NumberButton("8");
        JButton button9 = new NumberButton("9");
        JButton button4 = new NumberButton("4");
        JButton button5 = new NumberButton("5");
        JButton button6 = new NumberButton("6");
        JButton button1 = new NumberButton("1");
        JButton button2 = new NumberButton("2");
        JButton button3 = new NumberButton("3");
        JButton button10 = new NumberButton("0");
        JButton button11 = new NumberButton(".");
        JButton calc1 = new CalcButton("+");
        JButton calc2 = new CalcButton("-");
        JButton calc3 = new CalcButton("/");
        JButton calc4 = new CalcButton("*");
        /*addで表示*/
        key.add(button1);
        key.add(button2);
        key.add(button3);
        key.add(button4);
        key.add(button5);
        key.add(button6);
        key.add(button7);
        key.add(button8);
        key.add(button9);
        key.add(button10);
        key.add(button11);
        key.add(calc1);
        key.add(calc2);
        key.add(calc3);
        key.add(calc4);

        JButton clear = new ClearButton();
        key.add(clear);
        JButton total = new CalcButton("=");
        key.add(total);

        Container contentPane = getContentPane();
        /*それぞれのボタンを置く位置をNorth center southで表している*/
        contentPane.add(p, BorderLayout.NORTH);
        contentPane.add(key, BorderLayout.CENTER);
        contentPane.add(keisan, BorderLayout.SOUTH);
     }



public class NumberButton extends JButton implements ActionListener {

    public NumberButton(String keyTop){
      /*superは親クラスのコンストラクタを呼ぶこともできる
       super()は一世代以上の親クラスのコンストラクタを指す
       super()が活躍するのは親クラスのコンストラクタに引数を渡したい時
       今回はJButtonのコンストラクタにボタンの番号を送っている*/
        super(keyTop);
        this.addActionListener(this);
    }
  
    /*↓クリックされた時の動作*/
    public void actionPerformed(ActionEvent evt) {
      /*クリックで送信された内容をgetTextで取得してaboutbuttonに入れる*/
        String aboutbutton = this.getText();
        appendResult(aboutbutton);
    }
}

/*↓cの中にはクリックしたボタンの番号が入っている
押すたびにその押したボタンが入っていく
cの中には演算子は入らない*/
public void appendResult(String c) {
     /*afterCalcはboolean型でfalseが代入されている*/
    if(!afterCalc){
       /*totalは計算結果欄のインスタンス化されたやつ。それにgetTextで取ってきた押されたボタンの情報を
       setTextでtotalにセットする*/
       /*setText() は、テキストビューに任意の文字列をセットできるメソッドである。
      　totalに入っている数字にC(押されたボタン)を加える。*/
        total.setText(total.getText() + c);
    }else {
        total.setText(c);
        afterCalc =false;
    }
}



public class ClearButton extends JButton implements ActionListener {
    /*↓これがクリアボタンのコンストラクタ*/
    public ClearButton() {
        super("クリア");
        /*↓addActionListener というメソッドによって、対象のボタンにイベント処理機能を追加することができる。
        これによって、ボタンが押された際に発生したイベント (ボタンによって自動的に発生する) が、登録したリスナー
        の actionPerformed メソッドに自動的に渡されてイベント処理が行われる。*/

        /*「this」は「このインスタンス」という意味*/
        this.addActionListener(this);
    }
    /*クリアボタンを押された時の処理*/
    public void actionPerformed(ActionEvent evt) {
        /*クリアを押されたら以下のように全てに空欄を入れる*/
        total.setText("");
        stackedValue = 0.0;
        isStacked = false;
        boolean afterCalc = false;
        currentOp = "";
    }
}



public class CalcButton extends JButton implements ActionListener{

    public CalcButton(String cancel){
        super(cancel);
        this.addActionListener(this);
    }
    /*演算子ボタンが押された時の処理*/
    public void actionPerformed(ActionEvent e){
        /*isStackedは変巣定義されていてfalseがデフォルトになっている*/
        if(isStacked){
        /*valueOfでDouble型にする*/
        /*intValueメソッドはオブジェクトが持つ値をint型の値として返します。またIntegerクラスには値を
       long型の値として返すlongValueメソッドやdouble型の値として返すdoubleValueメソッドなども用意
       されています*/
       /*total.getTextで結果欄に入っている数値を取り出してdouble型にしてresultValueに代入する。*/
       /*total変数orオブジェクト内にあるgetTextメソッドを引き出している*/
        double resultValue = (Double.valueOf(total.getText())).doubleValue();
        /*currentOpはString型で''が代入されている*/
        /*もしもcurrentOpがequals内の演算子と==の場合。*/
        if ( currentOp.equals("+"))
            /*stackedValueはdouble型で0.0が代入されている*/
            stackedValue += resultValue;
        else if( currentOp.equals("-"))
            stackedValue -= resultValue;
        else if( currentOp.equals("*"))
            stackedValue *= resultValue;
        else if( currentOp.equals("/"))
            stackedValue /= resultValue;
        /*結果の欄に計算した数値が入るようにしている↓*/
        total.setText(String.valueOf(stackedValue));
}

        currentOp = this.getText();
        stackedValue = (Double.valueOf(total.getText())).doubleValue();

        afterCalc =true;

        if( currentOp.equals("計算")){
            isStacked = false;
        }else{
             isStacked = true;
        }
    }
}
}
