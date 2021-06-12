import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Objects extends Frame implements Observer, ActionListener {

    public static int width = 500;
    public static int height = 500;
    private LinkedList<Object> linkedList = new LinkedList<>();
    private HashSet<Integer> hashSetID = new HashSet<>();
    private Frame f;//Управляющее окно
    private Button addButton;
    private Button changeButton;
    private Choice objectType;
    private Choice objectColor;
    private Choice objectSpeed;
    private Choice objectChoice;
    private Choice objectNewSpeed;
    private TextField objectNumber;
    private TextField objectNewNumber;
    private TextField objectName;


    Objects() {
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                width = componentEvent.getComponent().getWidth();
                height = componentEvent.getComponent().getHeight();
            }
        });
        this.addWindowListener(new WindowAdapter2());
        f = new Frame();
        f.setSize(new Dimension(320, 280));
        f.setResizable(false);
        f.setTitle("Настройки");
        f.setLayout(null);
        f.addWindowListener(new WindowAdapter2());

        Label labelType = new Label("Название объекта");
        labelType.setBounds(10, 40, 150, 20);
        objectType = new Choice();
        objectType.addItem("Надпись");
        objectType.addItem("Картинка");
        objectType.setBounds(160, 40, 150, 20);
        f.add(labelType);
        f.add(objectType);

        Label labelColor = new Label("Выбор цвета");
        labelColor.setBounds(10, 65, 150, 20);
        objectColor = new Choice();
        objectColor.addItem("Синий");
        objectColor.addItem("Зелёный");
        objectColor.addItem("Красный");
        objectColor.addItem("Чёрный");
        objectColor.addItem("Жёлтый");
        objectColor.setBounds(160, 65, 150, 20);
        f.add(labelColor);
        f.add(objectColor);

        Label labelStartSpeed = new Label("Выбор объекта");
        labelStartSpeed.setBounds(10, 90, 150, 20);
        objectChoice = new Choice();
        objectChoice.addItem("");
        objectChoice.setBounds(160, 90, 150, 20);
        f.add(labelStartSpeed);
        f.add(objectChoice);

        Label labelChoiceObject = new Label("Начальная скорость");
        labelChoiceObject.setBounds(10, 115, 150, 20);
        objectSpeed = new Choice();
        objectSpeed.addItem("1");
        objectSpeed.addItem("2");
        objectSpeed.addItem("3");
        objectSpeed.addItem("4");
        objectSpeed.addItem("5");
        objectSpeed.addItem("6");
        objectSpeed.setBounds(160, 115, 150, 20);
        f.add(labelChoiceObject);
        f.add(objectSpeed);

        Label labelNumber = new Label("Номер объекта");
        labelNumber.setBounds(10, 140, 150, 20);
        objectNumber = new TextField();
        objectNumber.setBounds(160, 140, 150, 21);
        f.add(labelNumber);
        f.add(objectNumber);

        Label labelNewNumber = new Label("Изменить номер");
        labelNewNumber.setBounds(10, 165, 150, 20);
        objectNewNumber = new TextField();
        objectNewNumber.setBounds(160, 165, 150, 21);
        f.add(labelNewNumber);
        f.add(objectNewNumber);

        Label labelNewSpeed = new Label("Изменить скорость");
        labelNewSpeed.setBounds(10, 190, 150, 20);
        objectNewSpeed = new Choice();
        objectNewSpeed.addItem("1");
        objectNewSpeed.addItem("2");
        objectNewSpeed.addItem("3");
        objectNewSpeed.addItem("4");
        objectNewSpeed.addItem("5");
        objectNewSpeed.addItem("6");
        objectNewSpeed.setBounds(160, 190, 150, 21);
        f.add(labelNewSpeed);
        f.add(objectNewSpeed);

        Label labelObjectName = new Label("Текст надписи");
        labelObjectName.setBounds(10, 215, 150, 20);
        objectName = new TextField();
        objectName.setBounds(160, 215, 150, 21);
        f.add(labelObjectName);
        f.add(objectName);

        addButton = new Button("Добавить");
        addButton.setBounds(10, 240, 150, 30);
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        f.add(addButton);

        changeButton = new Button("Изменить");
        changeButton.setBounds(160, 240, 150, 30);
        changeButton.setActionCommand("change");
        changeButton.addActionListener(this);
        f.add(changeButton);
        f.setLocation(600, 300);
        f.setVisible(true);
        this.setSize(width, height);
        this.setLocation(0, 0);
        this.setTitle("Демонстрация");
        this.setVisible(true);
        this.setVisible(true);
    }

    //Вызывается, когда происходит действие
    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        boolean label = objectType.getSelectedIndex() == 0;
        //Добавить
        if (str.equals("add")){
            int speed = objectSpeed.getSelectedIndex();
            int id;
            try {
                id = Integer.parseInt(objectNumber.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Номер объекта должен быть целочисленным!");
                return;
            }
            if(hashSetID.contains(id)) {
                System.out.println("Номер объекта должен быть уникальным!");
                return;
            }
            if(label) {
                String text  = objectName.getText();
                if(text.equals("")) {
                    System.out.println("Введите текст надписи!");
                    return;
                }
                hashSetID.add(id);
                Color color = null;
                switch (objectColor.getSelectedIndex()) {
                    case 0: color = Color.blue; break;
                    case 1: color = Color.green; break;
                    case 2: color = Color.red; break;
                    case 3: color = Color.black; break;
                    case 4: color = Color.yellow; break;
                }
                MyLabel myLabel = new MyLabel(id, color, text, speed);
                myLabel.addObserver(this);
                linkedList.add(myLabel);
                objectChoice.addItem(String.valueOf(id));
                objectChoice.select(String.valueOf(id));
                objectNumber.setText("");
                objectName.setText("");
            } else {
                hashSetID.add(id);
                MyImage myImage = new MyImage(id, speed);
                myImage.addObserver(this);
                linkedList.add(myImage);
                objectChoice.addItem(String.valueOf(id));
                objectChoice.select(String.valueOf(id));
                objectNumber.setText("");
                objectName.setText("");
            }
        } else if(str.equals("change")) {//изменить
            String choice = objectChoice.getSelectedItem();
            if(choice.equals("")) {
                System.out.println("Выберите номер объекта из списка!");
                return;
            }
            int choiceID = Integer.parseInt(choice);
            int newSpeed = objectNewSpeed.getSelectedIndex();
            String newNumber  = objectNewNumber.getText();
            int newID;
            if(newNumber.equals("")) newID = choiceID;
            else {
                try {
                    newID = Integer.parseInt(newNumber);
                } catch (NumberFormatException ex) {
                    System.out.println("Новый номер объекта должен быть целочисленным!");
                    return;
                }
                if(hashSetID.contains(newID) && newID != choiceID) {
                    System.out.println("Новый номер объекта должен быть уникальным!");
                    return;
                }
            }
            hashSetID.remove(choiceID);
            hashSetID.add(newID);
            objectChoice.addItem(String.valueOf(newID));
            objectChoice.remove(String.valueOf(choiceID));
            objectChoice.select(String.valueOf(newID));
            for (Object item : linkedList) {
                if(item instanceof MyLabel) {
                    MyLabel myLabel = (MyLabel) item;
                    if(myLabel.getId() == choiceID) {
                        myLabel.setId(newID);
                        myLabel.setSpeed(newSpeed);
                        break;
                    }
                } else {
                    MyImage myImage = (MyImage) item;
                    if(myImage.getId() == choiceID) {
                        myImage.setId(newID);
                        myImage.setSpeed(newSpeed);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    public void paint (Graphics g) {
        if (!linkedList.isEmpty()){
            for (Object item : linkedList) {
                if(item instanceof MyLabel) {
                    MyLabel myLabel = (MyLabel) item;
                    g.setColor(myLabel.getColor());
                    g.drawString(myLabel.getText() + " " + myLabel.getId(), myLabel.getX(), myLabel.getY());
                } else {
                    MyImage myImage = (MyImage) item;
                    g.drawImage(myImage.getImage(), myImage.getX(), myImage.getY(), null);
                    g.setColor(Color.red);
                    g.drawString(String.valueOf(myImage.getId()), myImage.getX() + myImage.getWidth(), myImage.getY());
                }
            }
        }
    }
}