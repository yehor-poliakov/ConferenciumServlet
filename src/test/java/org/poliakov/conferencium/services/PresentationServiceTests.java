package org.poliakov.conferencium.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.poliakov.conferencium.dao.presentation.PresentationDao;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PresentationServiceTests {
    @Mock
    private PresentationDao presentationDao;

    private PresentationService presentationService;

    @Before
    public void init() {
        presentationService = new PresentationServiceImpl(presentationDao);
    }

    @Test
    public void shouldCreatePresentation() {
        // arrange
        Presentation presentation = mock(Presentation.class);
        when(presentationDao.createPresentation(presentation)).thenReturn(presentation);

        // act
        Presentation actual = presentationService.createPresentation(presentation);

        // assert
        verify(presentationDao, only()).createPresentation(any());
        Assert.assertEquals(presentation, actual);
    }

    @Test
    public void shouldSetSpeakerApprovedToFalseIfSpeakerIdIsNull() {
        // arrange
        Presentation presentation = mock(Presentation.class);
        presentation.setSpeakerId(null);
        presentation.setSpeakerApproved(true);
        when(presentationDao.createPresentation(presentation)).thenReturn(presentation);

        // act
        Presentation actual = presentationService.createPresentation(presentation);

        // assert
        verify(presentationDao, only()).createPresentation(any());
        Assert.assertEquals(presentation, actual);
        Assert.assertFalse(presentation.isSpeakerApproved());
    }
}
