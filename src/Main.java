import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        String input = "asecondclasscarriageonthetrain"; // character : 30
        String key = "351642";
        int blockSize = key.length(); // khối lượng phần tử trong 1 khối

        //  Số lượng khối cần chia
        int noBlockSize = (int) Math.ceil((double) input.length() / blockSize);

        // Tạo mảng để lưu các khối
        String[] blockInputArray = new String[noBlockSize];

        // Chia chuỗi thành các khối và lưu vào mảng
        for (int i = 0; i < noBlockSize; i++) {
            int startIndex = i * blockSize;
            int endIndex = Math.min(startIndex + blockSize, input.length());
            blockInputArray[i] = input.substring(startIndex, endIndex); // lấy khoảng chuỗi
        }


//-------------------------------------------------------------------------
//        Quá trình mã hóa

//        Khởi tạo array để tạo các dãy số trong mảng có các chữ số key
        Object[][] keyArray = new Object[noBlockSize][blockSize];
        for(int i = 0; i < noBlockSize; i++) {
            for(int k = 0; k < blockSize; k++) {
                char characterNoOfKey = key.charAt(k); // Láy các kí tự của khóa
                int valueNoOfKey = Character.getNumericValue(characterNoOfKey); // chuyển dổi kiểu char của key sang int

                keyArray[i][k] = valueNoOfKey;

            }
        }

//        Nhóm các kí tự trong từng khối block input để tạo mảng 2 chiều
//        Object[][] convertBlockInputToMatrix = new Object[noBlockSize][blockSize];
//        for(int i = 0; i < noBlockSize; i++) { // noBlockSize = 6
//            for(int k = 0; k < Math.min(blockSize, noBlockSize); k++) { // blockSize 6
//                String characterStrOfBlockInput = blockInputArray[i].charAt(k) + "";
//
//                convertBlockInputToMatrix[i][k] = characterStrOfBlockInput;
//            }
//        }

        Object[][] convertBlockInputToMatrix = new Object[noBlockSize][blockSize];

        for (int i = 0; i < noBlockSize; i++) {
            String currentBlockInput = blockInputArray[i];
            for (int k = 0; k < Math.min(blockSize, currentBlockInput.length()); k++) { // blockSize: 6 // currentBlockInput.length()
                // nếu phần tử trong block input ít hơn blocksize thì duyệt qua phần tử hiện có
                convertBlockInputToMatrix[i][k] = currentBlockInput.charAt(k);
            }
        }


//        Hoán vị các vị trí của phần tử trong các mảng để mã hóa
        Object[][] blockOutputEncrypt = new Object[noBlockSize][blockSize]; // lưu mảng mã hóa
        for(int i = 0; i < noBlockSize; i++) { // lấy vị trí của mảng trong mảng 2 chiều

            for (int k = 0; k < blockSize; k++) {
                int count = k + 1; // sử dụng các vị trí bắt đều từ 1

                for (int r = 0; r < blockSize; r++) {

                    if (count == (int)keyArray[i][r]) {
                        blockOutputEncrypt[i][r] = convertBlockInputToMatrix[i][k];
                        // trong đó i đang xét vị trí của mảng trong mảng 2 chiều
//                        r xét các phần tử trong từng mảng
                        if(blockOutputEncrypt[i][r] == null) { // kiểm tra phần tử trong mảng 2 chiều bằng null thì lấy chuỗi là rỗng
                            blockOutputEncrypt[i][r] = "";
                        }
                        break;
                    }

                }

            }
        }

        System.out.println(Arrays.deepToString(blockOutputEncrypt));

        StringBuilder stringBuilder = new StringBuilder(); // sử dụng method này để có thể chập dữ liệu thành một chuỗi
        for (int i = 0; i < noBlockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                stringBuilder.append(blockOutputEncrypt[i][j]);
            }
        }
        String appendedString = stringBuilder.toString();
        System.out.println("Ta có bản mã hóa: ");
        System.out.print(appendedString);

// -------------------------------------------------------------------------- //
// quá trình giải mã

//      Đưa các chữ số của khóa vào từng vị trí của mảng
        int[] keyFirstArr = new int[blockSize]; //
        for(int i = 0; i < blockSize; i++) {
            char characterKey = key.charAt(i);
            int convertCharacterKeyToInt = Character.getNumericValue(characterKey);

            keyFirstArr[i] = convertCharacterKeyToInt;
        }

//        for (int character : keyFirstArr) {
//            System.out.print(character + " ");
//        }

//      Chuyển khóa phép hoán vị ban đầu sang khóa hoán vị ngược
        int[] keyPermutationOppositeArr = new int[blockSize]; // mảng chứa khóa hoán vị ngược
        for(int i = 0; i < blockSize; i++) {
            for(int k = 0; k < blockSize; k++) {
                int count = k + 1;
                if(keyFirstArr[i] == count) {
                    keyPermutationOppositeArr[k] = i + 1;
                    break;
                }
            }
        }

//      Tạo mảng 2 chiều để chứa hóa vị ngược cho từng vị trí trong mảng của mảng 2 chiều
        Object[][] convertKeyPermutationToMatrix = new Object[noBlockSize][blockSize]; //v mảng 2 chiều chứa hoán vị ngược
        for(int i = 0; i < noBlockSize; i++) {
            for(int k = 0; k < blockSize; k++) {
                convertKeyPermutationToMatrix[i][k] = keyPermutationOppositeArr[k];
            }
        }

        System.out.println();

        System.out.println(Arrays.deepToString(convertKeyPermutationToMatrix));

        System.out.println();

        for (int character: keyPermutationOppositeArr) {
            System.out.print(character + " ");
        }

//        Hoán vị ngược các phần tử trong mảng để giải mã

        Object[][] blockOutputDeCrypt = new Object[noBlockSize][blockSize]; //v
        for(int i = 0; i < noBlockSize; i++) {

            for(int k = 0; k < blockSize; k++) {
                int count = k + 1;
                for(int r = 0; r < blockSize; r++) {
                    if(count == (int)convertKeyPermutationToMatrix[i][r]) {
                        blockOutputDeCrypt[i][r] = blockOutputEncrypt[i][k];
                        break;
                    }
                }
            }
        }

        System.out.println();

        System.out.println("Quá trình giải mã hoàn tất");

        System.out.println(Arrays.deepToString(blockOutputDeCrypt));

        StringBuilder stringBuilderDecrypt = new StringBuilder(); // sử dụng method này để có thể chập dữ liệu thành một chuỗi
        for (int i = 0; i < noBlockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                stringBuilderDecrypt.append(blockOutputDeCrypt[i][j]);
            }
        }
        String appendedStrDecrypt = stringBuilderDecrypt.toString();
        System.out.println("Ta có bản mã hóa: ");
        System.out.print(appendedStrDecrypt);


    }
}