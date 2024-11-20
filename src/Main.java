import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int opcaoOrdencao = menuOrdenacao(scanner);
            if (opcaoOrdencao == 4) {
                System.out.println("Saindo do programa...");
                break;
            }

            int[] array = carregarNumeros(scanner);
            if (array == null) {
                System.out.println("Erro ao ler o arquivo. Encerrando o programa...");
                return;
            }

            resultadoOrdenado(array, opcaoOrdencao);
        }

        scanner.close();
    }

    public static int menuOrdenacao(Scanner scanner) {
        while (true) {
            System.out.println("Selecione o tipo de algoritmo de ordenação:");
            System.out.println("1 - InsertionSort");
            System.out.println("2 - BinaryInsertionSort");
            System.out.println("3 - MergeSort");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");

            try {
                int opcao = scanner.nextInt();
                if (opcao >= 1 && opcao <= 4) {
                    return opcao;
                }
                System.out.println("Opção inválida! Tente novamente.");
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
                scanner.next();
            }
        }
    }

    public static int menuNumeros(Scanner scanner) {
        while (true) {
            System.out.println("Selecione a quantidade de números:");
            System.out.println("1 - 1.000");
            System.out.println("2 - 5.000");
            System.out.println("3 - 10.000");
            System.out.print("Opção: ");
            try {
                int opcaoNumeros = scanner.nextInt();
                if (opcaoNumeros >= 1 && opcaoNumeros <= 3) {
                    return opcaoNumeros;
                }
                System.out.println("Opção inválida! Tente novamente.");
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
                scanner.next();
            }
        }
    }

    public static int[] carregarNumeros(Scanner scanner) {
        int opcaoQuantidadeNumeros = menuNumeros(scanner);

        String diretorio = System.getProperty("user.dir") + "\\src\\numeros\\";
        String arquivo = "";

        switch (opcaoQuantidadeNumeros) {
            case 1:
                arquivo = "1000_numbers.txt";
                break;
            case 2:
                arquivo = "5000_numbers.txt";
                break;
            case 3:
                arquivo = "10000_numbers.txt";
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
                return null;
        }

        return lerArquivo(diretorio + arquivo);
    }

    public static int[] lerArquivo(String nomeArquivo) {
        ArrayList<Integer> numeros = new ArrayList<>();
        try {
            File file = new File(nomeArquivo);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextInt()) {
                numeros.add(fileScanner.nextInt());
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

    public static void executarOrdenacao(int[] array, int opcaoOrdencao) {
        switch (opcaoOrdencao) {
            case 1:
                insertionSort(array);
                break;
            case 2:
                binaryInsertionSort(array, array.length);
                break;
            case 3:
                mergeSort(array, 0, array.length - 1);
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
                break;
        }
    }

    public static double calcularTempoMedio(int[] array, int opcaoOrdencao) {
        long totalTime = 0;

        for (int i = 0; i < 10; i++) {
            int[] arrayCopia = Arrays.copyOf(array, array.length);
            long startTime = System.nanoTime();
            executarOrdenacao(arrayCopia, opcaoOrdencao);
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);
        }

        double tempoMedio = totalTime / 10.0;

        return tempoMedio / 1_000_000.0;
    }

    public static void resultadoOrdenado(int[] array, int opcaoOrdencao) {
        int[] arrayCopia = Arrays.copyOf(array, array.length);
        executarOrdenacao(arrayCopia, opcaoOrdencao);

        double tempoMedio = calcularTempoMedio(array, opcaoOrdencao);

        System.out.println("Array ordenado: " + Arrays.toString(arrayCopia));
        System.out.println("Tempo medio levado: " + tempoMedio + " ms\n");
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