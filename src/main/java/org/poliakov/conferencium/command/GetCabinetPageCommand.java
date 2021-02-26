package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.presentation.MysqlPresentationDaoImpl;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.model.presentation.PresentationDetails;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;
import org.poliakov.conferencium.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCabinetPageCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetCabinetPageCommand.class);

    private static PresentationService presentationService;
    private static UserService userService;

    private static String cabinetPage;

    public GetCabinetPageCommand() {
        LOGGER.info("Starting GetCabinetPageCommand");

        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());
        cabinetPage = PageMappingProperties.CABINET_PAGE;
        userService = new UserServiceImpl(new MysqlUserDaoImpl());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[]... params) {
        String email = (String) request.getSession().getAttribute("email");

        String pageNumberStr = request.getParameter("pageNumber");
        Integer pageNumber = pageNumberStr == null ? 1 : Integer.parseInt(pageNumberStr);

        String pageSizeStr = request.getParameter("pageSize");
        Integer pageSize = pageSizeStr == null ? 10 : Integer.parseInt(pageSizeStr);

        Long speakerId = userService.findUserByEmail(email).getId();
        Page<PresentationDetails> page = presentationService.findAllBySpeakerId(speakerId, pageNumber, pageSize);
        request.setAttribute("page", page);
        return cabinetPage;
    }
}
