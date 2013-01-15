/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package netlib;

/**
 * Класс содержит коды завершения операций.
 * @author Dmitruj S. Shakhtanov
 */
public class consterr
{
    /**
     * Код успешного завершения операции
     */
    public static final byte NOT_ERR = 0;

    /**
     * Код отсутствия ответа от сервера
     */
    public static final byte ERR_SERVER_DONT_ANSWER = -1;

    /**
     * Код неверного ввода логина или пароля
     */
    public static final byte ERR_INCORRECT_LOGIN_OR_PASS = -2;

    /**
     * Код невозможности получить файл
     */
    public static final byte ERR_FAILED_TAKE_FILE = -3;

    /**
     * Код отрицательного создания файла и папки
     */
    public static final byte ERR_CANT_CREATE_FILE_OR_FOLDER = -4;

    /**
     * Код отрицательной отправки файла на фтп-сервер
     */
    public static final byte ERR_CANT_SEND_FILE_ON_FTP = -5;

    /**
     * Код отрицательного создания пакетного файла для 1С
     */
    public static final byte ERR_CANT_CREATE_PRM_FILE = -10;

    /**
     * Общая ошибка обмена
     */
    public static final byte ERR_EXCHANGE = -11;
    
    /**
     * Код ошибки чтения лог-файла
     */
    public static final byte ERR_CANT_READ_LOG = -12;

    /**
     * Код ошибки неудачного соединения с сервером
     */
    public static final byte ERR_CANT_CONNECT = -13;

    /**
     * Ошибка передачи файла на фтп сервер
     */
    public static final byte ERR_FAILED_PUT_FILE = -14;

    /**
     * Код ошибки отсутсвия файла
     */
    public static final byte ERR_FILE_NOT_FOUND = -15;

    /**
     * Код ошибки внезапного сакрыти соединения с сервером
     */
    public static final byte ERR_SERVER_CONNECTION_CLOSED = -16;

    /**
     *
     * @param err
     * @return
     */
    public static String PrintErr(byte err)
    {
        switch (err)
        {
            case NOT_ERR:
            {
                return "Операция прошла успешно";
            }
            case ERR_SERVER_DONT_ANSWER:
            {
               return "Ошибка "+ERR_SERVER_DONT_ANSWER+". ФТП-сервер не отвечает";
            }
            case ERR_INCORRECT_LOGIN_OR_PASS:
            {
                return "Ошибка "+ERR_INCORRECT_LOGIN_OR_PASS+". Неверный пароль или логин";
            }
            case ERR_FAILED_TAKE_FILE:
            {
                return "Ошибка "+ERR_FAILED_TAKE_FILE+". Не удалось получить файл с ФТП-сервера";
            }
            case ERR_CANT_CREATE_FILE_OR_FOLDER:
            {
                return "Ошибка "+ERR_CANT_CREATE_FILE_OR_FOLDER+". Не удалось создать файл или папку";
            }
            case ERR_CANT_SEND_FILE_ON_FTP:
            {
                return "Ошибка "+ERR_CANT_SEND_FILE_ON_FTP+". Не удалось отправить файл на ФПТ-сервер";
            }
            case ERR_CANT_CREATE_PRM_FILE:
            {
                return "Ошибка "+ERR_CANT_CREATE_PRM_FILE+". Не удалось создать файл настроек обмена";
            }
            case ERR_EXCHANGE:
            {
                return "Ошибка "+ERR_EXCHANGE+". ОШИБКА ОБМЕНА";
            }
            case ERR_CANT_READ_LOG:
            {
                return "Ошибка "+ERR_CANT_READ_LOG+". Не удалось прочитать лог-файл";
            }
            case ERR_CANT_CONNECT:
            {
                return "Ошибка "+ERR_CANT_CONNECT+". Не удалось подключиться";
            }
            case ERR_FILE_NOT_FOUND:
            {
                return "Ошибка "+ERR_FILE_NOT_FOUND+". Не найден файл";
            }
            case ERR_SERVER_CONNECTION_CLOSED:
            {
                return "Ошибка "+ERR_SERVER_CONNECTION_CLOSED+". Сервер преждевременно закрыл соединение";
            }
        }
        return "ОШИБКА ОБМЕНА";
    }
}
