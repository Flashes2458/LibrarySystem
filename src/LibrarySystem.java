import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Test 01
 *
 * LibrarySystem 图书馆管理系统
 *
 * @author Fukun Bo
 *
 * @date Oct 18 2022
 *
 */
public class LibrarySystem {                   //主类

    private static Catalog catalog = new Catalog();

    private static BufferedReader stdIn =
            new BufferedReader(new InputStreamReader(System.in));

    private static PrintWriter stdOut =
            new PrintWriter(System.out, true);

    private static PrintWriter stdErr =
            new PrintWriter(System.err, true);

    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

    public static final String INFO_SAVE_FILE = "src/catalog.dat";


    public static void loadCatalogFromFile(String fileName) {    //从文件中加载数据
        try (BufferedReader input =
                     new BufferedReader(
                             new FileReader(fileName))) {
            //读取每行数据并写入
            String line = input.readLine();
            while (line != null) {

                String[] inputItem = line.split("_");
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YMD);
                try {
                    if (inputItem[0].equals("Book")) {
                        Book book = new Book(inputItem[1], inputItem[2], dateFormat.parse(inputItem[3]), inputItem[4], Integer.parseInt(inputItem[5]));
                        catalog.addItem(book);
                    } else if (inputItem[0].equals("Recording")) {
                        Recording recording = new Recording(inputItem[1], inputItem[2], dateFormat.parse(inputItem[3]), inputItem[4], inputItem[5]);
                        catalog.addItem(recording);
                    }
                    line = input.readLine();
                } catch (Exception e) {
                    stdErr.println("Failed, the format of data is wrong!");
                    e.printStackTrace();
                    return;
                }

            }
            stdOut.println("Successfully load catalog from file!");

        } catch (Exception e) {
            stdErr.println("Failed load catalog from file，wrong with fileName!");
            e.printStackTrace();
        }
    }

    public static void saveCatalogToFile(String fileName) {                  //保存所有数据到指定文件中

        try (PrintWriter output = new PrintWriter(new FileWriter(fileName))) {
            Iterator<CatalogItem> catalogIterator = catalog.iterator();
            while (catalogIterator.hasNext()) {
                CatalogItem item = catalogIterator.next();
                output.println(item.toString());
                output.flush();
            }
            stdOut.println("Successfully save catalog to file!");

        } catch (Exception e) {
            stdErr.println("Failed to save catalog to file，wrong with fileName!");
            e.printStackTrace();
        }
    }

    public static void displayCatalogItems() {                      //输出所有数据
        stdOut.println("--------------------------------------------------------------------------------------------------------------------------");

        Iterator<CatalogItem> catalogItemIterator = catalog.iterator();
        int i = 0;
        while (catalogItemIterator.hasNext()) {
            CatalogItem catalogItem = catalogItemIterator.next();
            stdOut.println(i + " : " + catalogItem.toString());
            i++;
        }

        stdOut.println("--------------------------------------------------------------------------------------------------------------------------");
        stdOut.println("The number of catalogItems is: " + catalog.getNumberOfItems());
        stdOut.println("--------------------------------------------------------------------------------------------------------------------------");

    }

    public static void displayByTitle(String title){              //通过输入题目检索并输出对应的项
        CatalogItem displayItem = catalog.getItem(title);
        if(displayItem != null) {
            stdOut.println("Success, the details item information you search is: ");
            stdOut.println(displayItem.toString());
        } else {
            stdErr.println("Failed, the item you want to search is not exist!");
        }
    }


    public void addItem(CatalogItem item) {                          //添加指定的项

        Iterator<CatalogItem> catalogItemIterator = catalog.iterator();
        //判断code是否与已有目录项重复
        while(catalogItemIterator.hasNext()) {
            CatalogItem catalogItem = catalogItemIterator.next();
            if(item.getCode().equals(catalogItem.getCode())) {
                stdErr.println("Failed, the code of item already exist!");
                return;
            }
        }
        catalog.addItem(item);
        stdOut.println("Saved successfully!");

    }

    private void addNewItemByString(String[] arrayItem, SimpleDateFormat dateFormat)
                                                                    throws ParseException {     //根据输入的信息添加新数据项
        if(arrayItem.length == 6) {
            if(arrayItem[0].equals("Book")) {
                CatalogItem catalogItem = new Book(arrayItem[1], arrayItem[2], dateFormat.parse(arrayItem[3]), arrayItem[4], Integer.parseInt(arrayItem[5]));
                this.addItem(catalogItem);
            } else if (arrayItem[0].equals("Recording")) {
                CatalogItem catalogItem = new Recording(arrayItem[1], arrayItem[2], dateFormat.parse(arrayItem[3]), arrayItem[4], arrayItem[5]);
                this.addItem(catalogItem);
            } else {
                stdErr.println("Failed, Format of new item is wrong!");
            }
        } else {
            stdErr.println("Failed, Format of new item is wrong!");
        }
    }

    public static boolean isInteger(String str) {                          //判断输入的字符串是否是数值型
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public void deleteByTitleOrIndex(String str){                          //根据输入的数据类型（title还是index）来删除对应数据项
        CatalogItem deleteItem = null;
        if(isInteger(str)) {
            deleteItem = catalog.getItem(Integer.parseInt(str));
        }
        else {
            deleteItem = catalog.getItem(str);
        }
        if(deleteItem != null) {
            catalog.removeItem(deleteItem);
            stdOut.println("Deleted Successfully!");
        } else {
            stdErr.println("Failed, the item you want to delete is not exist!");
        }

    }


    public void printOptions() {                       //输出菜单目录

        stdOut.println("+---------------------------------------------------------------+");
        stdOut.println("| [1] Display all catalogItems;                                 |");
        stdOut.println("| [2] Display detail information of one item by Code;           |");
        stdOut.println("| [3] Add a new Book/Record;                                    |");
        stdOut.println("| [4] Delete a Book/Record by tittle or index number user input |");
        stdOut.println("| [5] Save all catalogItems to file with file name user input   |");
        stdOut.println("| [0] Exit                                                      |");
        stdOut.println("+---------------------------------------------------------------+");
        stdOut.println("使用上述功能，请输入对应的数字: ");

    }

    public void handleInput() {                        //根据用户输入来运行对应的功能模块

        try {
            String serialStr = stdIn.readLine();
            int serialNumber = Integer.parseInt(serialStr);

            while(serialNumber != 0){
                switch (serialNumber){
                    case 1: {this.displayCatalogItems();break;}
                    case 2: {
                        stdOut.println("Input the title of a item you want to search: ");
                        String title = stdIn.readLine();
                        this.displayByTitle(title);
                        break;
                    }
                    case 3: {
                        stdOut.println("Input the information of a item you want to add: ");
                        stdOut.println("Format: (Book/Recording_code_title_date_author/performer_numberOfPages/format)");
                        String strItem = stdIn.readLine();
                        String[] arrayItem = strItem.split("_");
                        SimpleDateFormat dateFormat = new SimpleDateFormat(LibrarySystem.DATE_FORMAT_YMD);
                        addNewItemByString(arrayItem, dateFormat);
                        break;
                    }
                    case 4:{
                        stdOut.println("Input the title or index of a item you want to delete: ");
                        String str = stdIn.readLine();
                        this.deleteByTitleOrIndex(str);
                        break;
                    }
                    case 5:{
                        stdOut.println("Input the fileName you want to save catalog: ");
                        String fileName = stdIn.readLine();
                        this.saveCatalogToFile(fileName);
                        break;
                    }
                    default:{stdErr.println("Failed, the serial number entered is wrong!");break;}
                }
                //执行完一次后，循环调用选项打印，直到用户选0退出
                this.printOptions();
                serialNumber = Integer.parseInt(stdIn.readLine());
            }
            stdOut.println("Exit Successfully!");
            //用户退出后记录对目录项列表信息进行的修改并保存
            this.saveCatalogToFile(INFO_SAVE_FILE);

        } catch (Exception e) {
            stdErr.println("Failed, the format of input is wrong!");
            e.printStackTrace();
            this.handleInput();
        }

    }


    public static void main(String[] args) {
        //新建借阅管理系统对象
        LibrarySystem librarySystem = new LibrarySystem();
        //读入默认数据文件
        librarySystem.loadCatalogFromFile(INFO_SAVE_FILE);
        //打印功能菜单
        librarySystem.printOptions();
        //处理用户输入信息
        librarySystem.handleInput();


    }
}
