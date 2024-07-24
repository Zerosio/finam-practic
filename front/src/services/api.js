import axios from "axios";

/**
 * Запрос на получение данных через апи ендпоинт.
 *
 * @param {Object} filters - Фильтры, которые идут в тело запроса.
 * @param {number} offset - Смещение(текущая страница) для пагинации.
 * @param {number} limit - Кол-во строк на странице.
 * @returns {Object} Данные или информация об ошибке.
 */
export const fetchData = async (filters, offset, limit) => {
	try {
		const response = await axios.post(
			`${process.env.REACT_APP_ENDPOINT_URL}/api/v1/firms/finance_instruments?offset=${offset}&limit=${limit}`,
			filters,
			{
				headers: {
					accept: "*/*",
					"Content-Type": "application/json",
				},
			}
		);

		return {
			success: true,
			data: response.data.content,
			totalElements: response.data.totalElements,
		};
	} catch (error) {
		if (error.response) {
			// Сервер ответил статусом, отличным от 2xx
			console.error("Ошибка ответа сервера:", error.response.data);
			console.error("Статус ответа:", error.response.status);
		} else if (error.request) {
			// Запрос был сделан, но ответа не получено
			console.error(
				"Запрос был сделан, но ответа не получено",
				error.request
			);
		} else {
			// Произошло что-то при настройке запроса, вызвавшее ошибку
			console.error("Ошибка настройки запроса:", error.message);
		}

		return {
			success: false,
			error: error.response
				? error.response.data.message
				: "Не удалось подключиться к бекенду",
		};
	}
};
