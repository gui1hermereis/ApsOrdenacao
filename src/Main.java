import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcaoSorteio;

        do {
            System.out.println("Selecione o tipo de algoritmo de ordenação:");
            System.out.println("1 - InsertionSort");
            System.out.println("2 - BinaryInsertionSort");
            System.out.println("3 - MergeSort");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");

            try {
                opcaoSorteio = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
                scanner.next();
                continue;
            }

            if (opcaoSorteio == 4) {
                System.out.println("Saindo do programa...");
                break;
            }

            int[] array = submenu(scanner);

            if (array == null) {
                System.out.println("Erro ao ler o arquivo. Encerrando o programa...");
                return;
            }

            long startTime, endTime, duration;

            if (opcaoSorteio == 1) {
                System.out.println("Você escolheu a opção 1: InsertionSort");
                startTime = System.nanoTime();
                insertionSort(array);
                endTime = System.nanoTime();
                duration = endTime - startTime;
                System.out.println("Array ordenado com InsertionSort: " + Arrays.toString(array));
                System.out.println("Tempo levado: " + (duration / 1_000_000.0) + " ms");

            } else if (opcaoSorteio == 2) {
                System.out.println("Você escolheu a opção 2: BinaryInsertionSort");
                startTime = System.nanoTime();
                binaryInsertionSort(array, array.length);
                endTime = System.nanoTime();
                duration = endTime - startTime;
                System.out.println("Array ordenado com BinaryInsertionSort: " + Arrays.toString(array));
                System.out.println("Tempo levado: " + (duration / 1_000_000.0) + " ms");

            } else if (opcaoSorteio == 3) {
                System.out.println("Você escolheu a opção 3: MergeSort");
                startTime = System.nanoTime();
                mergeSort(array, 0, array.length - 1);
                endTime = System.nanoTime();
                duration = endTime - startTime;
                System.out.println("Array ordenado com MergeSort: " + Arrays.toString(array));
                System.out.println("Tempo levado: " + (duration / 1_000_000.0) + " ms");

            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }

            System.out.println();
        } while (true);

        scanner.close();
    }

    public static int[] submenu(Scanner scanner) {
        int opcaoListaNumeros;
        String caminhoDiretorio = System.getProperty("user.dir") + "\\src\\";

        System.out.println("Selecione a quantidade de números:");
        System.out.println("1 - 1.000");
        System.out.println("2 - 5.000");
        System.out.println("3 - 10.000");
        System.out.print("Opção: ");

        try {
            opcaoListaNumeros = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
            scanner.next();
            return null;
        }

        String arquivo;

        if (opcaoListaNumeros == 1) {
            arquivo = "1000_numbers.txt";
        } else if (opcaoListaNumeros == 2) {
            arquivo = "5000_numbers.txt";
        } else if (opcaoListaNumeros == 3) {
            arquivo = "10000_numbers.txt";
        } else {
            System.out.println("Opção inválida! Tente novamente.");
            return null;
        }

        return lerArquivo(caminhoDiretorio + arquivo);
    }

    public static int[] lerArquivo(String nomeArquivo) {
        ArrayList<Integer> numeros = new ArrayList<>();

        try {
            File file = new File(nomeArquivo);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNext()) {
                if (fileScanner.hasNextInt()) {
                    numeros.add(fileScanner.nextInt());
                } else {
                    fileScanner.next();
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo " + nomeArquivo + " não encontrado.");
            return null;
        }

        int[] array = new int[numeros.size()];
        for (int i = 0; i < numeros.size(); i++) {
            array[i] = numeros.get(i);
        }

        return array;
    }

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

    public static void binaryInsertionSort(int[] array, int n) {
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int insertPos = binaryInsertion(array, key, 0, i - 1);

            for (int j = i - 1; j >= insertPos; j--) {
                array[j + 1] = array[j];
            }

            array[insertPos] = key;
        }
    }

    private static int binaryInsertion(int[] array, int key, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (array[mid] == key) {
                return mid + 1;
            } else if (array[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            merge(array, left, mid, right);
        }
    }

    public static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++)
            leftArray[i] = array[left + i];
        for (int i = 0; i < n2; i++)
            rightArray[i] = array[mid + 1 + i];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}